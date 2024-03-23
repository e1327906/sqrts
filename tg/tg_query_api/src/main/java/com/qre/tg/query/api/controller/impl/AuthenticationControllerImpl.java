package com.qre.tg.query.api.controller.impl;

import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.query.api.controller.AuthenticationController;
import com.qre.tg.query.api.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationControllerImpl
 *
 * @author Zaw
 * @since 1.0
 * <p>
 * <pre>
 * Revision History:
 * Version  Date            Author          Changes
 * ------------------------------------------------------------------------------------------------------------------------
 * 1.0      13/2/2024     Zaw           Initial Coding
 *
 * </pre>
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationControllerImpl.class);

    private final AuthenticationServiceImpl service;

    @PostMapping("/Register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRequest userRequest
    ) {
        AuthenticationResponse response;
        try {
            response = service.register(userRequest);
        } catch (IllegalArgumentException e) {
            logger.error("Bad Request during user registration: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unexpected error during user registration", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(response);
    }

}
