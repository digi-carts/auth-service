package com.digicart.auth.service;

import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import com.digicart.auth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class AuthService {

    private static final long ACCESS_TTL_MS  = 30L * 60 * 1000;
    private static final long REFRESH_TTL_MS = 7L  * 24 * 60 * 60 * 1000;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> login(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (Boolean.TRUE.equals(user.getBlocked()))
            throw new IllegalStateException("Account is blocked");
        if (user.getPasswordHash() == null || !passwordEncoder.matches(password, user.getPasswordHash()))
            throw new IllegalArgumentException("Invalid credentials");

        userRepository.save(user); // update lastLoginAt lazily via @LastModifiedDate
        return buildAuthResponse(user);
    }

    @Transactional
    public Map<String, Object> merchantRegister(String email, String password, String name, String phone) {
        if (userRepository.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Email already registered");

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setName(name);
        user.setPhone(phone);
        user.setRole(Role.merchant);
        user.setProvider("credentials");
        user = userRepository.save(user);
        return buildAuthResponse(user);
    }

    @Transactional
    public User createAdmin(String email, String password, String name, Role role) {
        if (userRepository.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Email already registered");

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setName(name);
        user.setRole(role);
        user.setProvider("credentials");
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(String userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash()))
            throw new IllegalArgumentException("Current password is incorrect");
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public Map<String, Object> refresh(String refreshToken) {
        try {
            SecretKey key = key();
            Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(refreshToken).getPayload();
            if (!"refresh".equals(claims.get("type", String.class)))
                throw new IllegalArgumentException("Invalid token type");

            User user = userRepository.findById(claims.getSubject())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
            if (Boolean.TRUE.equals(user.getBlocked()))
                throw new IllegalStateException("Account is blocked");
            return buildAuthResponse(user);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }
    }

    private Map<String, Object> buildAuthResponse(User user) {
        String accessToken  = buildToken(user, ACCESS_TTL_MS,  false);
        String refreshToken = buildToken(user, REFRESH_TTL_MS, true);
        Map<String, Object> userDto = Map.of(
            "id",              user.getId(),
            "email",           user.getEmail(),
            "name",            user.getName() != null ? user.getName() : "",
            "role",            user.getRole().name(),
            "storeId",         user.getStoreId() != null ? user.getStoreId() : "",
            "setupStatus",     user.getSetupStatus(),
            "setupWizardPage", user.getSetupWizardPage()
        );
        return Map.of("user", userDto, "accessToken", accessToken, "refreshToken", refreshToken);
    }

    private String buildToken(User user, long ttlMs, boolean isRefresh) {
        var builder = Jwts.builder()
            .subject(user.getId())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + ttlMs))
            .signWith(key());
        if (isRefresh) builder.claim("type", "refresh");
        else           builder.claim("role", user.getRole().name());
        return builder.compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
