package com.qre.tg.query.api.controller.impl;

import com.qre.tg.dto.base.APIResponse;
import com.qre.tg.dto.qr.ValidationRequest;
import com.qre.tg.query.api.controller.QRValidatorController;
import com.qre.tg.query.api.service.QRValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/validator")
@RequiredArgsConstructor
public class QRValidatorControllerImpl implements QRValidatorController {

    private final QRValidatorService qrValidatorService;

    @PostMapping("/Validate")
    @Override
    public ResponseEntity<APIResponse> validate(@RequestBody ValidationRequest request) throws Exception {
        qrValidatorService.validate(request);
        APIResponse apiResponse = APIResponse.builder()
                .responseCode(String.valueOf(HttpStatus.OK.value()))
                .responseMsg(HttpStatus.OK.getReasonPhrase())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
