package com.qre.tg.query.api.service.impl;

import com.qre.cmel.exception.ExceptionMsg;
import com.qre.tg.dao.ticket.TicketMasterRepository;
import com.qre.tg.dao.user.RoleRepository;
import com.qre.tg.dao.user.UserRepository;
import com.qre.tg.dto.auth.AuthenticationRequest;
import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.qr.PurchaseTicketRequest;
import com.qre.tg.dto.qr.TicketDetailResponse;
import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.entity.ticket.*;
import com.qre.tg.entity.user.RoleType;
import com.qre.tg.query.api.common.TicketStatusEnum;
import com.qre.tg.query.api.config.ApplicationProperties;
import com.qre.tg.query.api.config.JwtService;
import com.qre.tg.query.api.controller.impl.AuthenticationControllerImpl;
import com.qre.tg.query.api.controller.impl.TicketServiceControllerImpl;


import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketServiceImplTest {

    
    @Mock
    private TicketServiceImpl ticketService;

    @InjectMocks
    private TicketServiceControllerImpl ticketServiceController;

    @Mock
    private AuthenticationServiceImpl authService;

    @InjectMocks
    private AuthenticationControllerImpl authController;

    @Mock
    private TicketMasterRepository ticketMasterRepository;
    
    @Mock
    private JwtService jwtService;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private PurchaseTicketRequest purchaseTicketRequest;

    private TicketMaster ticketMaster;

    private Long currentTime = new Date().getTime();
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        PurchaseTicketRequest purchaseTicketRequest = PurchaseTicketRequest.builder()
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
                .build();
        this.purchaseTicketRequest = purchaseTicketRequest;
        TicketMaster ticketMaster = TicketMaster.builder()
                .ticketType(purchaseTicketRequest.getTicketType())
                .journeyTypeId(purchaseTicketRequest.getJourneyType())
                .serialNumber(ticketService.generateSerialNumber(purchaseTicketRequest))
                .issuerId(10)
                .creatorId(5) //tg_id
                .groupSize(purchaseTicketRequest.getGroupSize())
                .creationDateTime(new Date())
                .effectiveDateTime(new Date())
                .operatorId(purchaseTicketRequest.getOperatorId()) // transport operator
                .validityDomain(1) // train/bus/flight/all
                .validityPeriod(ticketService.getValidityPeriod(new Date(purchaseTicketRequest.getStartDatetime()),
                        new Date(purchaseTicketRequest.getEndDatetime())))
                .transactionData(TransactionData.builder()
                        .paymentRefNo(purchaseTicketRequest.getPaymentRefNo())
                        .currency(purchaseTicketRequest.getCurrency())
                        .amount(purchaseTicketRequest.getAmount())
                        .build())
                .journeyDetails(ticketService.getJourneyDetails(purchaseTicketRequest))
                .additionalInfo(AdditionalInfo.builder().build())
                .build();
        ticketMaster.setCreatedDatetime(new Date());
        this.ticketMaster =ticketMaster;
    }


    UserRequest authicateUser() throws MessagingException, IOException {
        // Given
        UserRequest userRequest = UserRequest.builder()
                .userName("test")
                .email("test@gmail.com")
                .phoneNumber("1122334455")
                .password("test")
                .role(RoleType.ROLE_USER.name())
                .build();
        AuthenticationResponse mockResponse = AuthenticationResponse.builder()
                .accessToken("fakeAccessToken")
                .refreshToken("fakeRefreshToken")
                .build();

        when(authService.register(userRequest)).thenReturn(mockResponse);

        // When
        ResponseEntity<AuthenticationResponse> response = authController.register(userRequest);

        AuthenticationRequest authReq = AuthenticationRequest.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        ResponseEntity<AuthenticationResponse> res = authController.authenticate(authReq);
        System.out.println(authReq);
        return userRequest;
    }


    @Test
    public void testGuestUserCanPurchaseTicketSuccessful() throws Exception{
        Map<String, Object> ticketJsonData = ticketService.prepareTicketJsonData(ticketMaster);
        ticketMaster.setSecurity(Security.builder()
                .digitalSignature(ticketService.generateDigitalSignature(ticketJsonData))
                .build());
        ticketMaster.setQrData(ticketService.prepareQRData(ticketJsonData));
        ticketMaster.setEmail("guest@email.com");
        ticketMaster.setPhoneNo(null);
        when(ticketMasterRepository.save(any(TicketMaster.class))).thenReturn(ticketMaster);
        assertNotNull(ticketMaster);
    }

    @Test
    public void testRegiestedUserCanPurchaseTicketSuccessful() throws Exception{
        UserRequest registedUser =  authicateUser();
        Map<String, Object> ticketJsonData = ticketService.prepareTicketJsonData(ticketMaster);
        ticketMaster.setSecurity(Security.builder()
                .digitalSignature(ticketService.generateDigitalSignature(ticketJsonData))
                .build());
        ticketMaster.setQrData(ticketService.prepareQRData(ticketJsonData));
        ticketMaster.setEmail(registedUser.getEmail());
        ticketMaster.setPhoneNo(registedUser.getPhoneNumber());
        when(ticketMasterRepository.save(any(TicketMaster.class))).thenReturn(ticketMaster);
        assertNotNull(ticketMaster);
    }

//     @Test
//     void testFindAllByEmail_ReturnTicketList() throws Exception {
//         UserRequest registedUser =  authicateUser();
//         purchaseTicketRequest.setEmail(registedUser.getEmail());

//         List<JourneyDetails> journeyDetailsList = new ArrayList<>();

//         JourneyDetails journeyDetails = JourneyDetails.builder()
//                 .departurePoint(purchaseTicketRequest.getDeparturePoint())
//                 .arrivalPoint(purchaseTicketRequest.getArrivalPoint())
//                 .status(TicketStatusEnum.ACTIVE.getValue())
//                 .build();
//         journeyDetails.setCreatedDatetime(new Date());
//         journeyDetailsList.add(journeyDetails);


//         Map<String, Object> ticketJsonData = ticketService.prepareTicketJsonData(ticketMaster);
//         ticketMaster.setSecurity(Security.builder()
//                 .digitalSignature(ticketService.generateDigitalSignature(ticketJsonData))
//                 .build());
//         ticketMaster.setQrData(ticketService.prepareQRData(ticketJsonData));
//         ticketMaster.setEmail(registedUser.getEmail());
//         ticketMaster.setPhoneNo(registedUser.getPhoneNumber());
//         ticketMaster.setJourneyDetails(journeyDetailsList);
//         System.out.println(journeyDetailsList);


//         when(ticketMasterRepository.save(any(TicketMaster.class))).thenReturn(ticketMaster);
//         System.out.println(ticketMaster);

//         List<TicketDetailResponse> ticketList = ticketService.findAllByEmail(registedUser.getEmail());
//         System.out.println(ticketList);

// //        Assertions.assertThat(ticketList).isNotNull();
// //        Assertions.assertThat(ticketList).hasSizeGreaterThan(0);
//     }

//    @Test
//    void getJourneyDetail_ReturnJourneyDetailList() {
//        List<JourneyDetails> journeyDetailsList = ticketService.getJourneyDetails(request);
//        Assertions.assertThat(journeyDetailsList).isNotNull();
//        Assertions.assertThat(journeyDetailsList).hasSizeGreaterThan(0);
//    }


  
   

    // Add more test cases as needed
}