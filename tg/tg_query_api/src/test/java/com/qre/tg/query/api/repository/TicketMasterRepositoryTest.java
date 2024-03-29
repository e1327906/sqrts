package com.qre.tg.query.api.repository;

import com.qre.tg.dao.ticket.TicketMasterRepository;
import com.qre.tg.entity.ticket.TicketMaster;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

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
}
