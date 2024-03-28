package com.qre.tg.query.api.controller.impl;
import com.qre.tg.dto.base.APIResponse;
import com.qre.tg.dto.qr.PurchaseTicketRequest;
import com.qre.tg.query.api.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TicketServiceControllerImplTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketServiceControllerImpl ticketServiceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPurchaseTicket() throws Exception {
        Long currentTime = new Date().getTime();

        PurchaseTicketRequest request = PurchaseTicketRequest.builder()
                .journeyType(1)
                .groupSize(0)
                .operatorId(3)
                .startDatetime(currentTime)
                .endDatetime(currentTime + 3600000) // Adding 1 hour for testing
                .departurePoint(123)
                .arrivalPoint(456)
                .paymentRefNo("PAY123")
                .amount(1000L)
                .currency("USD")
                .phoneNo("+1234567890")
                .email("test@example.com")
                .build();

        ResponseEntity<APIResponse> responseEntity = ticketServiceController.purchaseTicket(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(ticketService, times(1)).purchaseTicket(request);
    }

    @Test
    public void testGetTickets() throws Exception {
        String email = "test@example.com";

        ResponseEntity<APIResponse> responseEntity = ticketServiceController.getTickets(email);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(ticketService, times(1)).findAllByEmail(email);
    }

    @Test
    public void testGetRefundTickets() throws Exception {
        String email = "test@example.com";

        ResponseEntity<APIResponse> responseEntity = ticketServiceController.getRefundTickets(email);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(ticketService, times(1)).findAllRefundableTicketByEmail(email);
    }
}