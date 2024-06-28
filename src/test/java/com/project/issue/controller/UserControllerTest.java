package com.project.issue.controller;

import com.project.issue.model.User;
import com.project.issue.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void accessUnprotectedUrl() throws Exception {
        mockMvc.perform(get("/api/users/unprotected"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is an unprotected endpoint"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("user1")))
                .andExpect(jsonPath("$[1].username", is("user2")));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testUser")));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("newUser");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"newUser\", \"password\": \"password\", \"email\": \"newUser@example.com\", \"name\": \"New User\", \"role\": \"user\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("newUser")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("updatedUser");

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(Optional.of(user));

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"updatedUser\", \"password\": \"password\", \"email\": \"updatedUser@example.com\", \"name\": \"Updated User\", \"role\": \"admin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updatedUser")));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"updatedUser\", \"password\": \"password\", \"email\": \"updatedUser@example.com\", \"name\": \"Updated User\", \"role\": \"admin\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserLoginHistory() throws Exception {
        Map<String, Object> login1 = new HashMap<>();
        login1.put("date", "2024-06-01T12:34:56");
        login1.put("ip", "192.168.0.1");

        Map<String, Object> login2 = new HashMap<>();
        login2.put("date", "2024-06-02T14:22:33");
        login2.put("ip", "192.168.0.2");

        when(userService.getUserLoginHistory(1L)).thenReturn(Arrays.asList(login1, login2));

        mockMvc.perform(get("/api/users/1/login-history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date", is("2024-06-01T12:34:56")))
                .andExpect(jsonPath("$[0].ip", is("192.168.0.1")))
                .andExpect(jsonPath("$[1].date", is("2024-06-02T14:22:33")))
                .andExpect(jsonPath("$[1].ip", is("192.168.0.2")));
    }
}
