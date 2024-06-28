package com.project.issue.service;

import com.project.issue.model.User;
import com.project.issue.model.LoginHistory;
import com.project.issue.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("newUser");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertEquals("newUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        User updatedUser = new User();
        updatedUser.setUsername("updatedUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        Optional<User> result = userService.updateUser(1L, updatedUser);
        assertTrue(result.isPresent());
        assertEquals("updatedUser", result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean isDeleted = userService.deleteUser(1L);
        assertTrue(isDeleted);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = userService.deleteUser(1L);
        assertFalse(isDeleted);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).delete(any(User.class));
    }

    @Test
    public void testGetUserLoginHistory() {
        User user = new User();
        user.setId(1L);

        LoginHistory login1 = new LoginHistory();
        login1.setDate(LocalDateTime.of(2024, 6, 1, 12, 34, 56));
        login1.setIp("192.168.0.1");

        LoginHistory login2 = new LoginHistory();
        login2.setDate(LocalDateTime.of(2024, 6, 2, 14, 22, 33));
        login2.setIp("192.168.0.2");

        when(userRepository.findLoginHistoryByUserId(1L)).thenReturn(Arrays.asList(login1, login2));

        List<Map<String, Object>> loginHistory = userService.getUserLoginHistory(1L);
        assertEquals(2, loginHistory.size());
        assertEquals("2024-06-01T12:34:56", loginHistory.get(0).get("date"));
        assertEquals("192.168.0.1", loginHistory.get(0).get("ip"));
        assertEquals("2024-06-02T14:22:33", loginHistory.get(1).get("date"));
        assertEquals("192.168.0.2", loginHistory.get(1).get("ip"));

        verify(userRepository, times(1)).findLoginHistoryByUserId(1L);
    }
}
