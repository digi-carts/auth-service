package com.digicart.auth.controller;

import com.digicart.auth.dto.AuthResponse;
import com.digicart.auth.dto.LoginRequest;
import com.digicart.auth.dto.RefreshRequest;
import com.digicart.auth.dto.RegisterRequest;
import com.digicart.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String MSG_KEY = "message";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/merchant-register")
    public ResponseEntity<AuthResponse> merchantRegister(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.registerMerchant(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(authService.refresh(req.refreshToken()));
    }

    @PostMapping("/firebase")
    public ResponseEntity<Map<String, Object>> firebaseAuth(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(Map.of("error", "Firebase auth not configured"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody Map<String, String> body) {
        authService.forgotPassword(body.get("email"));
        return ResponseEntity.ok(Map.of(MSG_KEY, "If that email exists, a reset link will be sent"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> body) {
        authService.resetPassword(body.get("token"), body.get("newPassword"));
        return ResponseEntity.ok(Map.of(MSG_KEY, "Password reset successful"));
    }

    @PatchMapping("/me/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, String> body) {
        authService.changePassword(userId, body.get("currentPassword"), body.get("newPassword"));
        return ResponseEntity.ok(Map.of(MSG_KEY, "Password changed"));
    }

    @PatchMapping("/setup-progress")
    public ResponseEntity<Map<String, Object>> setupProgress(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, Object> body) {
        String step = (String) body.get("step");
        Boolean completed = body.get("completed") instanceof Boolean b ? b : null;
        return ResponseEntity.ok(authService.updateSetupProgress(userId, step, completed));
    }

    @PatchMapping("/setup-complete")
    public ResponseEntity<Map<String, Object>> setupComplete(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody(required = false) Map<String, Object> body) {
        authService.markSetupComplete(userId);
        return ResponseEntity.ok(Map.of(MSG_KEY, "Setup complete"));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMe(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(Map.of("user", authService.getUser(userId)));
    }

    @PatchMapping("/me")
    public ResponseEntity<Map<String, Object>> updateMe(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("user", authService.updateProfile(userId, body)));
    }

    @PostMapping("/social")
    public ResponseEntity<AuthResponse> socialLogin(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(authService.socialLogin(body));
    }

    @PostMapping("/firebase/merchant")
    public ResponseEntity<AuthResponse> firebaseMerchantLogin(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(authService.firebaseMerchantLogin(body.get("email"), body.get("name")));
    }
}
