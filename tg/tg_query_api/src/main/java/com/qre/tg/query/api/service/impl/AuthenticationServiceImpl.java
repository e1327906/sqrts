package com.qre.tg.query.api.service.impl;

import com.qre.tg.dao.user.RoleRepository;
import com.qre.tg.dao.user.UserRepository;
import com.qre.tg.dto.auth.AuthenticationRequest;
import com.qre.tg.dto.auth.AuthenticationResponse;
import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.entity.user.Role;
import com.qre.tg.entity.user.RoleType;
import com.qre.tg.entity.user.User;
import com.qre.tg.query.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Set;

/**
 * AuthenticationServiceImpl
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
@Transactional
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository repository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  public AuthenticationResponse register(UserRequest userRequest) {

    // Create a Set<Role> with the USER role
    Set<Role> roles = Collections.singleton(roleRepository.findRoleByName(RoleType.valueOf(userRequest.getRole())));

    var user = User.builder()
            .userName(userRequest.getUserName())
            .email(userRequest.getEmail())
            .password(passwordEncoder.encode(userRequest.getPassword()))
            .roles(roles)
            .build();
    repository.save(user);
    var jwtToken = "";
    var refreshToken = "";
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();

  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    return null;
  }

  @Override
  public void saveUserToken(User user, String jwtToken) {

  }

  @Override
  public void revokeAllUserTokens(User user) {

  }

  @Override
  public AuthenticationResponse refreshToken(HttpServletRequest request) {
    return null;
  }

}
