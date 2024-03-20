package com.userauth.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.userauth.repository.UserRepository;

public class AuthControllerTest {

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;
    
    @Test
    void testLoginUser() {

    }

    @Test
    void testRegisterUser() {
    }
}
