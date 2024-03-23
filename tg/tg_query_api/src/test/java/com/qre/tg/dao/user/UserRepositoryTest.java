package com.qre.tg.dao.user;

import com.qre.tg.dto.user.UserRequest;
import com.qre.tg.entity.user.Role;
import com.qre.tg.entity.user.RoleType;
import com.qre.tg.entity.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser(){

        //given - precondition or setup
        Role role = new Role();
        role.setName(RoleType.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = User.builder()
                .userName("zaw")
                .email("zaw@gmail.com")
                .phoneNumber("1122334455")
                .password("zaw")
                .roles(roles)
                .build();

        // when - action or the behaviour that we are going test
        User savedUser = userRepository.save(user);

        // then - verify the output
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
    }
}