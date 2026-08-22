package com.digicart.auth.service;

import com.digicart.auth.dto.AuthResponse;
import com.digicart.auth.dto.LoginRequest;
import com.digicart.auth.dto.RegisterRequest;
import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import com.digicart.auth.exception.BadCredentialsException;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    private static final long ACCESS_TTL_MS  = 60 * 60 * 1000L;        // 1 hour
    private static final long REFRESH_TTL_MS = 7 * 24 * 60 * 60 * 1000L; // 7 days

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(BadCredentialsException::new);

        if (user.getPasswordHash() == null ||
                !passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new BadCredentialsException();
        }

        return buildResponse(user);
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalStateException("Email already registered");
        }
        User user = new User();
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user.setName(req.name());
        user.setPhone(req.phone());
        user.setProvider("credentials");
        user.setRole(Role.user);
        user = userRepository.save(user);
        return buildResponse(user);
    }

    public AuthResponse refresh(String refreshToken) {
        SecretKey key = signingKey();
        Claims claims;
        try {
            claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(refreshToken).getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String userId = claims.getSubject();
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return buildResponse(user);
    }

    private AuthResponse buildResponse(User user) {
        String accessToken  = buildToken(user, ACCESS_TTL_MS);
        String refreshToken = buildToken(user, REFRESH_TTL_MS);
        return new AuthResponse(user, accessToken, refreshToken);
    }

    private String buildToken(User user, long ttlMs) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole().name())
                .claim("email", user.getEmail())
                .issuedAt(new Date(now))
                .expiration(new Date(now + ttlMs))
                .signWith(signingKey())
                .compact();
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
