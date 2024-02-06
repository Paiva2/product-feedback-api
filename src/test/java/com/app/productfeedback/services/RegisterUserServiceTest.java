package com.app.productfeedback.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import com.app.productfeedback.entities.User;
import com.app.productfeedback.exceptions.BadRequestException;
import com.app.productfeedback.exceptions.ConflictException;
import com.app.productfeedback.repositories.UserRepositoryTest;
import com.app.productfeedback.services.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Service
public class RegisterUserServiceTest {
    protected BCrypt bcrypt = new BCrypt();

    @Autowired
    private UserRepositoryTest userRepositoryTest;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        this.userRepositoryTest = new UserRepositoryTest();
        this.userService = new UserService(userRepositoryTest);
    }

    @Test
    @DisplayName("it should register a new user")
    void shouldRegisterANewUser() {
        User newUser = new User();

        newUser.setEmail("johndoe@test.com");
        newUser.setPassword("123456");
        newUser.setUsername("John Doe");

        User userCreated = this.userService.register(newUser);

        Boolean doesPasswordsMatches = BCrypt.checkpw("123456", userCreated.getPassword());

        assertEquals("johndoe@test.com", userCreated.getEmail());
        assertEquals("John Doe", userCreated.getUsername());
        assertEquals(true, doesPasswordsMatches);
        assertThat(userCreated != null);
    }


    @Test
    @DisplayName("it not should register a new user if user already exists")
    void shouldNotRegisterANewUser() throws Exception {
        User newUser = new User();

        newUser.setEmail("johndoe@test.com");
        newUser.setPassword("123456");
        newUser.setUsername("John Doe");

        this.userService.register(newUser);

        Exception thrown = Assertions.assertThrows(ConflictException.class, () -> {
            this.userService.register(newUser);
        });

        Assertions.assertEquals("E-mail already exists.", thrown.getMessage());
    }

    @Test
    @DisplayName("it not should register a new user if password has less than 6 characters")
    void passwordLengthRegister() throws Exception {
        User newUser = new User();

        newUser.setEmail("johndoe@test.com");
        newUser.setPassword("12345");
        newUser.setUsername("John Doe");

        Exception thrown = Assertions.assertThrows(BadRequestException.class, () -> {
            this.userService.register(newUser);
        });

        Assertions.assertEquals("Password must have at least 6 characters.", thrown.getMessage());
    }

    @Test
    @DisplayName("it not should register a new user if parameter User is null")
    void nullUserRegister() throws Exception {
        User newUser = null;

        Exception thrown = Assertions.assertThrows(BadRequestException.class, () -> {
            this.userService.register(newUser);
        });

        Assertions.assertEquals("User can't be null.", thrown.getMessage());
    }

}