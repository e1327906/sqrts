package com.qre.tg.query.api.controller.impl;

import com.qre.tg.dto.auth.AuthenticationRequest;
import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.query.api.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationControllerImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationServiceImpl service;

    @PostMapping("/Register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRequest userRequest
    ) throws MessagingException, IOException {
        return ResponseEntity.ok(service.register(userRequest));
    }
    @PostMapping("/Authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        logger.info("AuthenticationControllerImpl.Authenticate {}", request);
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/RefreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

}