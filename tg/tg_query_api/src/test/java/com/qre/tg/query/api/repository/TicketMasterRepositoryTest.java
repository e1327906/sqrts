package com.qre.tg.query.api.repository;

import com.qre.tg.dao.ticket.TicketMasterRepository;
import com.qre.tg.dto.qr.TicketDetailResponse;
import com.qre.tg.entity.ticket.TicketMaster;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class TicketMasterRepositoryTest {

    @Mock
    private TicketMasterRepository ticketMasterRepository;

    @Test
    public void TicketMasterRepository_Save_ReturnSavedTicketMaster() {
        TicketMaster mockTicketMaster = Mockito.mock(TicketMaster.class);
        when(ticketMasterRepository.save(mockTicketMaster)).thenReturn(mockTicketMaster);
        TicketMaster savedticketMaster = ticketMasterRepository.save(mockTicketMaster);
        assertNotNull(savedticketMaster,"ticket master is not saved successfully");
    }

    @Test
    public void TicketMasterRepository_findAllByEmail_ReturnMatchingTicketMaster () {
        List<TicketMaster> ticketList = ticketMasterRepository.findAllByEmail("younmemeaung@gmail.com");
        Assertions.assertThat(ticketList).isNotNull();
        Assertions.assertThat(ticketList).hasSizeGreaterThan(0);
    }

    @Test
    public void TicketMasterRepository_findAllBySerialNumber_ReturnMatchingTicketMaster () {
        Optional<TicketMaster> ticketList = ticketMasterRepository.findAllBySerialNumber("test_serial_number");
        Assertions.assertThat(ticketList).isNotNull();
        Assertions.assertThat(ticketList).isNotEmpty();
    }

    @Test
    public void TicketMasterRepository_findByTransactionDataPaymentRefNo_ReturnMatchingTicketMaster () {
        Optional<TicketMaster> ticketList = ticketMasterRepository.findByTransactionDataPaymentRefNo("test_transaction_data_payment_number");
        Assertions.assertThat(ticketList).isNotNull();
        Assertions.assertThat(ticketList).isNotEmpty();
    }
}
