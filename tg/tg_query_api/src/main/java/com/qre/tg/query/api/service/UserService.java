
package com.qre.tg.query.api.service;

import com.qre.tg.dto.user.ChangePasswordRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface UserService extends UserDetailsService{

    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
