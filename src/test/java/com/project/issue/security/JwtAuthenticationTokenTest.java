package com.project.issue.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationTokenTest {

    private JwtAuthenticationToken jwtAuthenticationToken;
    private String principal;
    private String credentials;
    private Collection<SimpleGrantedAuthority> authorities;

    @BeforeEach
    void setUp() {
        principal = "testUser";
        credentials = "testCredentials";
        authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        jwtAuthenticationToken = new JwtAuthenticationToken(principal, credentials, authorities);
    }

    @Test
    void testGetCredentials() {
        assertEquals(credentials, jwtAuthenticationToken.getCredentials());
    }

    @Test
    void testGetPrincipal() {
        assertEquals(principal, jwtAuthenticationToken.getPrincipal());
    }

    @Test
    void testGetAuthorities() {
        assertNotNull(jwtAuthenticationToken.getAuthorities());
        assertEquals(2, jwtAuthenticationToken.getAuthorities().size());
        assertTrue(jwtAuthenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(jwtAuthenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testIsAuthenticated() {
        assertTrue(jwtAuthenticationToken.isAuthenticated());
    }

    @Test
    void testSetAuthenticated() {
        jwtAuthenticationToken.setAuthenticated(false);
        assertFalse(jwtAuthenticationToken.isAuthenticated());
    }

    @Test
    void testToString() {
        String expected = "JwtAuthenticationToken[principal=testUser, credentials=testCredentials, authenticated=true, authorities=[ROLE_USER, ROLE_ADMIN]]";
        assertTrue(jwtAuthenticationToken.toString().contains("principal=testUser"));
        assertTrue(jwtAuthenticationToken.toString().contains("credentials=testCredentials"));
        assertTrue(jwtAuthenticationToken.toString().contains("authenticated=true"));
        assertTrue(jwtAuthenticationToken.toString().contains("ROLE_USER"));
        assertTrue(jwtAuthenticationToken.toString().contains("ROLE_ADMIN"));
    }
}
