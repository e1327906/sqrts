package com.qre.tg.query.api.controller.impl;

import com.qre.tg.dto.base.APIResponse;
import com.qre.tg.dto.qr.PurchaseTicketRequest;
import com.qre.tg.query.api.controller.TicketServiceController;
import com.qre.tg.query.api.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets/")
@RequiredArgsConstructor
public class TicketServiceControllerImpl implements TicketServiceController {

    private final TicketService ticketService;

    @PostMapping("/PurchaseTicket")
    @Override
    public ResponseEntity<APIResponse> purchaseTicket(@RequestBody PurchaseTicketRequest request) throws Exception {
        ticketService.purchaseTicket(request);
        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK))
                .responseMsg(HttpStatus.OK.getReasonPhrase())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/Tickets")
    @Override
    public ResponseEntity<APIResponse> getTickets(@RequestParam String email) throws Exception {

        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK))
                .responseMsg(HttpStatus.OK.getReasonPhrase())
                .responseData(ticketService.findAllByEmail(email))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/RefundTickets")
    @Override
    public ResponseEntity<APIResponse> getRefundTickets(String email) throws Exception {
        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK))
                .responseMsg(HttpStatus.OK.getReasonPhrase())
                .responseData(ticketService.findAllRefundableTicketByEmail(email))
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
