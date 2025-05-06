package com.artocons.carshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @BeforeEach
    public void setUp() {
       SecurityContextHolder.clearContext();
    }

    @Test
    public void testGetIsAdmin_WhenUserIsAdmin_ShouldReturnTrue() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", "password", Collections.singletonList(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertTrue(authService.getIsAdmin());
    }

    @Test
    public void testGetIsAdmin_WhenUserIsNotAdmin_ShouldReturnFalse() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password", Collections.singletonList(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertFalse(authService.getIsAdmin());
    }

    @Test
    public void testGetIsAdmin_WhenNoAuthentication_ShouldReturnFalse() {

       assertFalse(authService.getIsAdmin());
    }
}
