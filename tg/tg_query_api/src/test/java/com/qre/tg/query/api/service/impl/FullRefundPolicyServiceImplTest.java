package com.qre.tg.query.api.service.impl;

import org.junit.jupiter.api.Test;

import com.qre.tg.entity.ticket.TicketMaster;
import com.qre.tg.entity.ticket.TransactionData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;

public class FullRefundPolicyServiceImplTest {

    private FullRefundPolicyServiceImpl refundPolicyService;

    @BeforeEach
    public void setUp() {
      refundPolicyService = new FullRefundPolicyServiceImpl();
    }

    @Test
    public void testCalculateRefund() {
      // Mocking TicketMaster and TransactionData
      TicketMaster ticketMaster = mock(TicketMaster.class);
      TransactionData transactionData = mock(TransactionData.class);

      // Stubbing the method calls
      when(ticketMaster.getTransactionData()).thenReturn(transactionData);
      when(transactionData.getAmount()).thenReturn(100L);

      // Calculate refund
      long expectedRefund = 94;
      long actualRefund = refundPolicyService.calculateRefund(ticketMaster);

      // Verify the refund amount
      assertEquals(expectedRefund, actualRefund);
    }
}