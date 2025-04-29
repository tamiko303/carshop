package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.User;
import com.artocons.carshop.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testFindByUsername_UserExists() {
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        User result = userService.findByUsername(username);

        assertEquals(expectedUser, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    public void testFindByUsername_UserNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        User result = userService.findByUsername(username);

        assertNull(result);
        verify(userRepository).findByUsername(username);
    }
}
