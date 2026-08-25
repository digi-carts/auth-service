package com.digicart.auth.service;

import com.digicart.auth.dto.AuthResponse;
import com.digicart.auth.dto.LoginRequest;
import com.digicart.auth.dto.RegisterRequest;
import com.digicart.auth.dto.UserDto;
import com.digicart.auth.entity.PasswordResetToken;
import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import com.digicart.auth.exception.BadCredentialsException;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.PasswordResetTokenRepository;
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
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private static final long ACCESS_TTL_MS  = 60 * 60 * 1000L;        // 1 hour
    private static final long REFRESH_TTL_MS = 7 * 24 * 60 * 60 * 1000L; // 7 days
    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public AuthService(UserRepository userRepository,
                       PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
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

    @Transactional
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

    @Transactional
    public AuthResponse registerMerchant(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = new User();
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        user.setName(req.name());
        user.setPhone(req.phone());
        user.setProvider("credentials");
        user.setRole(Role.merchant);
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
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        return buildResponse(user);
    }

    @Transactional
    public void forgotPassword(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            tokenRepository.deleteByEmail(email);
            PasswordResetToken prt = new PasswordResetToken();
            prt.setEmail(email);
            prt.setToken(UUID.randomUUID().toString());
            prt.setExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS));
            tokenRepository.save(prt);
        });
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        if (token == null || newPassword == null) {
            throw new IllegalArgumentException("Token and newPassword are required");
        }
        PasswordResetToken prt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));
        if (prt.getExpiresAt().isBefore(Instant.now())) {
            tokenRepository.delete(prt);
            throw new IllegalArgumentException("Token has expired");
        }
        User user = userRepository.findByEmail(prt.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(prt);
    }

    @Transactional
    public void changePassword(String userId, String currentPassword, String newPassword) {
        if (currentPassword == null || newPassword == null) {
            throw new IllegalArgumentException("currentPassword and newPassword are required");
        }
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if (user.getPasswordHash() == null ||
                !passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public Map<String, Object> updateSetupProgress(String userId, String step, Boolean completed) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if (step != null) {
            user.setSetupStatus(step);
        }
        userRepository.save(user);
        return Map.of(
                "step", step != null ? step : "",
                "completed", Boolean.TRUE.equals(completed)
        );
    }

    @Transactional
    public void markSetupComplete(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setSetupStatus("COMPLETED");
        userRepository.save(user);
    }

    public UserDto getUser(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return UserDto.from(user);
    }

    @Transactional
    public UserDto updateProfile(String userId, Map<String, Object> body) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if (body.containsKey("name")) user.setName((String) body.get("name"));
        if (body.containsKey("phone")) user.setPhone((String) body.get("phone"));
        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public AuthResponse socialLogin(Map<String, Object> body) {
        String provider = (String) body.get("provider");
        String providerAccountId = (String) body.get("providerAccountId");
        String email = (String) body.get("email");
        String name = (String) body.get("name");
        String storeId = (String) body.get("storeId");

        User user = null;
        if (provider != null && providerAccountId != null) {
            user = userRepository.findByProviderAndProviderAccountId(provider, providerAccountId).orElse(null);
        }
        if (user == null && email != null) {
            user = userRepository.findByEmail(email).orElse(null);
            if (user != null && provider != null) {
                user.setProvider(provider);
                user.setProviderAccountId(providerAccountId);
            }
        }
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setProvider(provider != null ? provider : "social");
            user.setProviderAccountId(providerAccountId);
            user.setRole(Role.user);
            if (storeId != null) user.setStoreId(storeId);
        }
        return buildResponse(userRepository.save(user));
    }

    @Transactional
    public AuthResponse firebaseMerchantLogin(String email, String name) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setProvider("firebase");
            user.setRole(Role.merchant);
        }
        return buildResponse(userRepository.save(user));
    }

    private AuthResponse buildResponse(User user) {
        String accessToken  = buildToken(user, ACCESS_TTL_MS);
        String refreshToken = buildToken(user, REFRESH_TTL_MS);
        return new AuthResponse(UserDto.from(user), accessToken, refreshToken);
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
