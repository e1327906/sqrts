

package com.qre.tg.query.api.service.impl;

import com.qre.cmel.exception.*;
import com.qre.tg.dao.fare.TrainFareMatrixRepository;
import com.qre.tg.dto.fare.FareResponse;
import com.qre.tg.dto.fare.TrainFareRequest;
import com.qre.tg.entity.base.DbFieldName;
import com.qre.tg.entity.fare.TrainFareMatrix;
import com.qre.tg.entity.fare.TrainFareMatrixPK;
import com.qre.tg.query.api.common.JourneyTypeEnum;
import com.qre.tg.query.api.service.TrainFareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TrainFareServiceImpl implements TrainFareService {

    private final TrainFareMatrixRepository trainFareMatrixRepository;

    @Override
    public FareResponse findFare(TrainFareRequest request) {

        TrainFareMatrixPK pk = TrainFareMatrixPK.builder()
                .destStnId(request.getDestStnId())
                .srcStnId(request.getSrcStnId())
                .ticketTypeId(request.getTicketType())
                .build();

        TrainFareMatrix fareMatrixOutbound = trainFareMatrixRepository.findById(pk)
                .orElseThrow(() -> new FareNotFoundException(ExceptionMsg.FARE_NOT_FOUND));
        TrainFareMatrix fareMatrixReturn = null;

        if (JourneyTypeEnum.RETURN_TICKET.equals(JourneyTypeEnum.fromValue(request.getTicketType()))) {
            // For return journey, source and destination are reversed
            pk.setSrcStnId(request.getDestStnId());
            pk.setDestStnId(request.getSrcStnId());
            fareMatrixReturn = trainFareMatrixRepository.findById(pk).orElse(null);
        }


        Long fareAmountOutbound = fareMatrixOutbound.getFare();
        Long totalFare;

        if (fareMatrixReturn != null) {
            Long fareAmountReturn = fareMatrixReturn.getFare();
            totalFare = fareAmountOutbound + fareAmountReturn;
        }
        else {
            totalFare = fareAmountOutbound; // Only outbound fare
        }

        // Multiply total fare by number of passengers for group fare
        totalFare *= request.getGroupSize();

        return FareResponse.builder()
                .fare(totalFare)
                .build();
    }

    @Override
    public List<TrainFareMatrix> findAllFare() {
        return trainFareMatrixRepository.findAll();
    }
}
