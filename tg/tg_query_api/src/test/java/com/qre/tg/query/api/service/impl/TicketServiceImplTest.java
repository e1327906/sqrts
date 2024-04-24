package com.qre.tg.query.api.service.impl;

import com.qre.cmel.exception.ExceptionMsg;
import com.qre.tg.dao.ticket.TicketMasterRepository;
import com.qre.tg.dao.user.RoleRepository;
import com.qre.tg.dao.user.UserRepository;
import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.qr.PurchaseTicketRequest;
import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.entity.ticket.AdditionalInfo;
import com.qre.tg.entity.ticket.TicketMaster;
import com.qre.tg.entity.ticket.TransactionData;
import com.qre.tg.entity.user.Role;
import com.qre.tg.entity.user.RoleType;
import com.qre.tg.entity.user.User;
import com.qre.tg.entity.ticket.Security;
import com.qre.tg.query.api.controller.impl.TicketServiceControllerImpl;
import com.qre.tg.query.api.service.TicketService;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceImplTest {

    
    @Mock
    private TicketServiceImpl ticketService;

    @InjectMocks
    private TicketServiceControllerImpl ticketServiceController;

    @Mock
    private TicketMasterRepository ticketMasterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPurchaseTicket() throws Exception{
        Long currentTime = new Date().getTime();

        // UserRequest userRequest = UserRequest.builder()
        //         .userName("zaw")
        //         .email("zaw@gmail.com")
        //         .phoneNumber("1122334455")
        //         .password("zaw")
        //         .role(RoleType.ROLE_USER.name())
        //         .build();
        // Role role = new Role();
        // role.setName(RoleType.ROLE_USER);
        // when(roleRepository.findRoleByName(RoleType.ROLE_USER)).thenReturn(role);
        // Set<Role> roles = new HashSet<>();
        // roles.add(role);

        // User user = User.builder()
        //         .userName(userRequest.getUserName())
        //         .email(userRequest.getEmail())
        //         .phoneNumber(userRequest.getPhoneNumber())
        //         .password(passwordEncoder.encode(userRequest.getPassword()))
        //         .roles(roles)
        //         .build();
        // // Stubbing userRepository.save() to accept any User object
        // when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

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

        // TicketMaster ticketMaster = TicketMaster.builder()
        //         .ticketId(1)
        //         .ticketType(1)
        //         .journeyTypeId(1)
        //         .serialNumber(ticketService.generateSerialNumber(req))
        //         .groupSize(1)
        //         .creatorId(5)
        //         .validityPeriod(0)
        //         .operatorId(0)
        //         .creationDateTime(currentTime)
        //         .effectiveDateTime(currentTime + 3600000) // Adding 1 hour for testing
        //         .issuerId(10)
        //         .validityDomain(1)
        //         .userId(1)
        //         .phoneNo("")
        //         .email("test@example.com")
        //         .journeyDetails(ticketService.getJourneyDetails(req))
        //         .qrData(prepareQRData)
        //         .build();
        //Date dd = new Date().getTime();
         TicketMaster ticketMaster = TicketMaster.builder()
                .ticketType(request.getTicketType())
                .journeyTypeId(request.getJourneyType())
                .serialNumber(ticketService.generateSerialNumber(request))
                .issuerId(10)
                .creatorId(5) //tg_id
                .groupSize(request.getGroupSize())
                .phoneNo(request.getPhoneNo())
                .email(request.getEmail())
                .creationDateTime(new Date())
                .effectiveDateTime(new Date())
                .operatorId(request.getOperatorId()) // transport operator
                .validityDomain(1) // train/bus/flight/all
                .validityPeriod(ticketService.getValidityPeriod(new Date(request.getStartDatetime()),
                        new Date(request.getEndDatetime())))
                .transactionData(TransactionData.builder()
                        .paymentRefNo(request.getPaymentRefNo())
                        .currency(request.getCurrency())
                        .amount(request.getAmount())
                        .build())
                .journeyDetails(ticketService.getJourneyDetails(request))
                .additionalInfo(AdditionalInfo.builder().build())
                .build();
        Map<String, Object> ticketJsonData = ticketService.prepareTicketJsonData(ticketMaster);
        ticketMaster.setSecurity(Security.builder()
                .digitalSignature(ticketService.generateDigitalSignature(ticketJsonData))
                .build());
        ticketMaster.setQrData(ticketService.prepareQRData(ticketJsonData));
        ticketMaster.setCreatedDatetime(new Date());

        when(ticketMasterRepository.save(any(TicketMaster.class))).thenReturn(ticketMaster);
        assertNotNull(ticketMaster);

    }
  
   

    // Add more test cases as needed
}