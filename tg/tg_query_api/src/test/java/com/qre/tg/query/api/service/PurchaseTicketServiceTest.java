package com.qre.tg.query.api.service;

import com.qre.tg.dao.ticket.TicketMasterRepository;
import com.qre.tg.dao.user.RoleRepository;
import com.qre.tg.dao.user.UserRepository;
import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.qr.PurchaseTicketRequest;
import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.entity.ticket.AdditionalInfo;
import com.qre.tg.entity.ticket.Security;
import com.qre.tg.entity.ticket.TicketMaster;
import com.qre.tg.entity.ticket.TransactionData;
import com.qre.tg.entity.user.Role;
import com.qre.tg.entity.user.RoleType;
import com.qre.tg.entity.user.User;
import com.qre.tg.query.api.service.impl.AuthenticationServiceImpl;
import com.qre.tg.query.api.service.impl.TicketServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PurchaseTicketServiceTest {
    @Mock
    private TicketServiceImpl ticketService;

    @Mock
    private TicketMasterRepository ticketMasterRepository;
    @Test
    void purchaseTicket_ReturnTicketMaster() throws Exception {

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

       TicketMaster ticketMaster = ticketService.purchaseTicket(request);
       Assertions.assertThat(ticketMaster).isNotNull();
    }

}
