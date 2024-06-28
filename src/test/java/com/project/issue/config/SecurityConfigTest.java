package com.project.issue.config;

import com.project.issue.filter.JwtAuthenticationFilter;
import com.project.issue.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .addFilters(new JwtAuthenticationFilter(jwtUtil))
                .build();
    }

    private String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    @Test
    public void loginUser() throws Exception {
        when(jwtUtil.extractUsername(anyString())).thenReturn("user");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginAdmin() throws Exception {
        when(jwtUtil.extractUsername(anyString())).thenReturn("admin");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\", \"password\":\"admin\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginInvalidUser() throws Exception {
        when(jwtUtil.extractUsername(anyString())).thenReturn("invalid");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"invalid\", \"password\":\"invalid\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void accessProtectedUrlWithUser() throws Exception {
        String token = generateToken("user");

        mockMvc.perform(get("/api/protected-url")
                        .header("Authorization", "Bearer " + token))
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void accessProtectedUrlWithAdmin() throws Exception {
        String token = generateToken("admin");

        mockMvc.perform(get("/api/protected-url")
                        .header("Authorization", "Bearer " + token))
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void logoutUser() throws Exception {
        String token = generateToken("user");

        mockMvc.perform(post("/api/users/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }

    @Test
    public void accessUnprotectedUrl() throws Exception {
        mockMvc.perform(get("/api/unprotected-url"))
                .andExpect(status().isOk());
    }
}
