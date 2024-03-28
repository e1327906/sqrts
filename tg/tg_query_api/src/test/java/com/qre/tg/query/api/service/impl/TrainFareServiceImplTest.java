package com.qre.tg.query.api.service.impl;

import com.qre.cmel.exception.ExceptionMsg;
import com.qre.cmel.exception.FareNotFoundException;
import com.qre.tg.dao.fare.TrainFareMatrixRepository;
import com.qre.tg.dto.fare.BusFareRequest;
import com.qre.tg.dto.fare.FareResponse;
import com.qre.tg.dto.fare.TrainFareRequest;
import com.qre.tg.entity.fare.BusFareMatrix;
import com.qre.tg.entity.fare.TrainFareMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TrainFareServiceImplTest {

    @Mock
    private TrainFareMatrixRepository repository;

    @InjectMocks
    private TrainFareServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindFare_OutboundJourney() {
        TrainFareRequest request = new TrainFareRequest();
        request.setSrcStnId(1);
        request.setDestStnId(2);
        request.setTicketType(1);
        request.setGroupSize(1);

        TrainFareMatrix fareMatrixOutbound = new TrainFareMatrix();
        fareMatrixOutbound.setFare(100L);

        when(repository.findById(any())).thenReturn(Optional.of(fareMatrixOutbound));
        FareResponse response = service.findFare(request);
        assertEquals(100, response.getFare(), "Fare must be 100");
    }

    @Test
    public void testFindFare_ReturnJourney() {
        TrainFareRequest request = new TrainFareRequest();
        request.setSrcStnId(1);
        request.setDestStnId(2);
        request.setTicketType(1);
        request.setGroupSize(1);

        TrainFareMatrix fareMatrixOutbound = new TrainFareMatrix();
        fareMatrixOutbound.setFare(100L);

        TrainFareMatrix fareMatrixReturn = new TrainFareMatrix();
        fareMatrixReturn.setFare(150L);

        when(repository.findById(any())).thenReturn(Optional.of(fareMatrixOutbound), Optional.of(fareMatrixReturn));

        FareResponse response = service.findFare(request);

        assertEquals(250, response.getFare(), "Fare must be 250");
    }

    @Test
    public void testFindFare_FareNotFoundException() {
        TrainFareRequest request = new TrainFareRequest();
        request.setSrcStnId(1);
        request.setDestStnId(2);
        request.setTicketType(1);
        request.setGroupSize(1);

        when(repository.findById(any())).thenReturn(Optional.empty());

        try {
            service.findFare(request);
        } catch (Exception e) {
            assertEquals(FareNotFoundException.class, e.getClass());
            assertEquals(ExceptionMsg.FARE_NOT_FOUND, e.getMessage());
        }
    }

    // Add more test cases as needed
}