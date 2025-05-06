package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.User;
import com.artocons.carshop.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEnabled("true");

        userRepository.save(user);
    }

    @Test
    void findByUsername_existingUser_returnsUser() {
        User found = userService.findByUsername("testuser");
        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
    }

    @Test
    void findByUsername_nonExistingUser_returnsNull() {
        User found = userService.findByUsername("nonexistent");
        assertNull(found);
    }
}
