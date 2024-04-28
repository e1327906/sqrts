package com.qre.tg.query.api.service.impl;

import com.qre.tg.entity.ticket.TicketMaster;
import com.qre.tg.entity.ticket.TransactionData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartialRefundPolicyServiceImplTest {

  private PartialRefundPolicyServiceImpl refundPolicyService;

  @BeforeEach
  public void setUp() {
    refundPolicyService = new PartialRefundPolicyServiceImpl();
  }

  @Test
  public void testCalculateRefund_Within48Hours() {
    // Mocking TicketMaster and TransactionData
    TicketMaster ticketMaster = mock(TicketMaster.class);
    TransactionData transactionData = mock(TransactionData.class);

    // Stubbing the method calls
    when(ticketMaster.getEffectiveDateTime()).thenReturn(Date.from(Instant.now().minus(Duration.ofHours(24))));
    when(ticketMaster.getTransactionData()).thenReturn(transactionData);
    when(transactionData.getAmount()).thenReturn(100L);

    // Calculate refund
    long actualRefund = refundPolicyService.calculateRefund(ticketMaster);
    long expectedRefund = 50L;

    // Verify the refund amount
    assertEquals(expectedRefund, actualRefund, "Refund should be 50%");
  }

  @Test
  public void testCalculateRefund_Within72Hours() {
    // Mocking TicketMaster and TransactionData
    TicketMaster ticketMaster = mock(TicketMaster.class);
    TransactionData transactionData = mock(TransactionData.class);

    // Stubbing the method calls
    when(ticketMaster.getEffectiveDateTime()).thenReturn(Date.from(Instant.now().minus(Duration.ofHours(70))));
    when(ticketMaster.getTransactionData()).thenReturn(transactionData);
    when(transactionData.getAmount()).thenReturn(100L);

    // Calculate refund
    long actualRefund = refundPolicyService.calculateRefund(ticketMaster);
    long expectedRefund = 25L;

    // Verify the refund amount
    assertEquals(expectedRefund, actualRefund, "Refund should be 25%");
  }

  @Test
  public void testCalculateRefund_After72Hours() {
    // Mocking TicketMaster and TransactionData
    TicketMaster ticketMaster = mock(TicketMaster.class);
    TransactionData transactionData = mock(TransactionData.class);

    // Stubbing the method calls
    when(ticketMaster.getEffectiveDateTime()).thenReturn(Date.from(Instant.now().minus(Duration.ofHours(80))));
    when(ticketMaster.getTransactionData()).thenReturn(transactionData);
    when(transactionData.getAmount()).thenReturn(100L);

    // Calculate refund
    long actualRefund = refundPolicyService.calculateRefund(ticketMaster);
    long expectedRefund = 0L;

    // Verify the refund amount
    assertEquals(expectedRefund, actualRefund, "Refund should be 0%");
  }
}