package com.project.issue.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    private String secret = "aVerySecretKeyThatIsAtLeast32CharactersLong"; // 至少32个字符
    private long expiration = 3600000; // 1 hour

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();

        // 使用ReflectionTestUtils注入私有字段
        ReflectionTestUtils.setField(jwtUtil, "secret", secret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", expiration);

        // 手动调用 init 方法以初始化密钥
        jwtUtil.init();
    }

    @Test
    public void testGenerateToken() {
        String token = jwtUtil.generateToken("testUser");
        assertNotNull(token);

        String username = jwtUtil.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    public void testExtractUsername() {
        String token = jwtUtil.generateToken("testUser");
        String username = jwtUtil.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    public void testExtractExpiration() {
        String token = jwtUtil.generateToken("testUser");
        Date expirationDate = jwtUtil.extractExpiration(token);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    public void testValidateToken() {
        String token = jwtUtil.generateToken("testUser");
        assertTrue(jwtUtil.validateToken(token, "testUser"));
    }

    @Test
    public void testIsTokenExpired() {
        String token = jwtUtil.generateToken("testUser");
        assertFalse(jwtUtil.extractExpiration(token).before(new Date()));
    }

    @Test
    public void testExtractAllClaims() {
        String token = jwtUtil.generateToken("testUser");
        Claims claims = jwtUtil.extractAllClaims(token);
        assertEquals("testUser", claims.getSubject());
    }

    @Test
    public void testInvalidToken() {
        String invalidToken = "invalidToken";
        assertThrows(Exception.class, () -> {
            jwtUtil.extractAllClaims(invalidToken);
        });
    }
}
