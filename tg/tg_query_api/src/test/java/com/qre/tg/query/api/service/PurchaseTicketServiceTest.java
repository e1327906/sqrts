package com.qre.tg.query.api.service;

import com.qre.tg.dao.ticket.TicketMasterRepository;
import com.qre.tg.dao.user.RoleRepository;
import com.qre.tg.dao.user.UserRepository;
import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.qr.PurchaseTicketRequest;
import com.qre.tg.dto.qr.TicketDetailResponse;
import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.entity.ticket.*;
import com.qre.tg.entity.user.Role;
import com.qre.tg.entity.user.RoleType;
import com.qre.tg.entity.user.User;
import com.qre.tg.query.api.service.impl.AuthenticationServiceImpl;
import com.qre.tg.query.api.service.impl.TicketServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PurchaseTicketServiceTest {
    @Mock
    private TicketServiceImpl ticketService;

    @Mock
    private TicketMasterRepository ticketMasterRepository;

    private PurchaseTicketRequest request;
    @Before
    public void setUp() throws Exception {
        PurchaseTicketRequest request = new PurchaseTicketRequest();
        request.setJourneyType(2);
        request.setGroupSize(3);
        request.setEndDatetime(new Date().getTime() + 3600 * 1000); // Adding one hour
        request.setPaymentRefNo("REF123");
        request.setAmount(100L);
        request.setCurrency("USD");
        request.setPhoneNo("1234567890");
        request.setEmail("younmemeaung@gmail.com");
        request.setDeparturePoint(1);
        request.setArrivalPoint(2);
        request.setPaymentRefNo("test_payment");
        request.setAmount(0L);
        request.setCurrency("test_currency");

    }
    @Test
    void purchaseTicket_ReturnTicketMaster() throws Exception {
       TicketMaster ticketMaster = ticketService.purchaseTicket(request);
       Assertions.assertThat(ticketMaster).isNotNull();
    }

    @Test
    void findAllByEmail_ReturnTicketList() throws Exception {
        List<TicketDetailResponse> ticketList = ticketService.findAllByEmail("younmemeaung@gmail.com");
        Assertions.assertThat(ticketList).isNotNull();
        Assertions.assertThat(ticketList).hasSizeGreaterThan(0);
    }

    @Test
    void getJourneyDetail_ReturnJourneyDetailList() {
        List<JourneyDetails> journeyDetailsList = ticketService.getJourneyDetails(request);
        Assertions.assertThat(journeyDetailsList).isNotNull();
        Assertions.assertThat(journeyDetailsList).hasSizeGreaterThan(0);
    }
}
