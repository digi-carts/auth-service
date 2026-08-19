package com.digicart.auth.controller;

import com.digicart.auth.dto.*;
import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import com.digicart.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.getEmail(), req.getPassword()));
    }

    @PostMapping("/merchant-register")
    public ResponseEntity<Map<String, Object>> merchantRegister(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.merchantRegister(req.getEmail(), req.getPassword(), req.getName(), req.getPhone()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(@RequestBody RefreshTokenRequest req) {
        return ResponseEntity.ok(authService.refresh(req.getRefreshToken()));
    }

    @PostMapping("/admin-mgmt")
    public ResponseEntity<User> createAdmin(
            @Valid @RequestBody AdminCreateRequest req,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"superadmin".equals(userRole))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.createAdmin(req.getEmail(), req.getPassword(), req.getName(), Role.merchant));
    }

    @PostMapping("/admin-mgmt/superadmin")
    public ResponseEntity<User> createSuperAdmin(
            @Valid @RequestBody AdminCreateRequest req,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"superadmin".equals(userRole))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.createAdmin(req.getEmail(), req.getPassword(), req.getName(), Role.superadmin));
    }

    @PostMapping("/admin-mgmt/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        if (userId == null || userId.isBlank())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        authService.changePassword(userId, req.getCurrentPassword(), req.getNewPassword());
        return ResponseEntity.noContent().build();
    }
}
