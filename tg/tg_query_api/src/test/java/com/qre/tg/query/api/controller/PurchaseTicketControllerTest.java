package com.qre.tg.query.api.controller;
import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.base.APIResponse;
import com.qre.tg.dto.qr.PurchaseTicketRequest;
import com.qre.tg.entity.ticket.TicketMaster;
import com.qre.tg.query.api.controller.impl.TicketServiceControllerImpl;
import com.qre.tg.query.api.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PurchaseTicketControllerTest {

    @Mock
    private TicketServiceImpl service;

    @InjectMocks
    private TicketServiceControllerImpl controller;

    @Mock
    TicketMasterRepository ticketMasterRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    void testPurchaseTicket() throws Exception {
        PurchaseTicketRequest request = new PurchaseTicketRequest();
        request.setJourneyType(2);
        request.setGroupSize(3);
        request.setStartDatetime(new Date().getTime());
        request.setEndDatetime(new Date().getTime() + 3600 * 1000); // Adding one hour
        request.setPaymentRefNo("REF123");
        request.setAmount(100L);
        request.setCurrency("USD");
        request.setPhoneNo("1234567890");
        request.setEmail("example@example.com");

//        TicketMaster ticket = new TicketMaster();
//        ticket.setTicketType(1);
//        ticket.setJourneyTypeId(2);
//        ticket.setSerialNumber("ABC123");

        ResponseEntity<APIResponse> response = controller.purchaseTicket(request);
        assertNotNull(response.getBody(), "Response body should return purchased ticket");
        assertEquals( 200, response.getStatusCodeValue());
    }

    void testGetTickets() throws Exception {
        ResponseEntity<APIResponse> response = controller.getTickets("younmemeaung@gmail.com");
        assertNotNull(response.getBody(), "Response body should return all tickets");
        assertEquals( 200, response.getStatusCodeValue());
    }

    void testGetRefundTickets() throws Exception {
        ResponseEntity<APIResponse> response = controller.getRefundTickets("younmemeaung@gmail.com");
        assertNotNull(response.getBody(), "Response body should return all refund tickets");
        assertEquals( 200, response.getStatusCodeValue());
    }
}
