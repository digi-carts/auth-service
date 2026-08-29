package com.digicart.auth.controller;

import com.digicart.auth.dto.CreatePasswordResetTokenRequest;
import com.digicart.auth.entity.PasswordResetToken;
import com.digicart.auth.service.PasswordResetTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing password reset token HTTP APIs for <em>auth-service</em>.
 */
@RestController
@RequestMapping("/password-reset-tokens")
public class PasswordResetTokenController {

    private final PasswordResetTokenService tokenService;

    public PasswordResetTokenController(PasswordResetTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/{id}")
    public PasswordResetToken findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return tokenService.findById(id);
    }

    @GetMapping("/token/{token}")
    public PasswordResetToken findByToken(
            @PathVariable String token,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return tokenService.findByToken(token);
    }

    @PostMapping
    public ResponseEntity<PasswordResetToken> create(
            @Valid @RequestBody CreatePasswordResetTokenRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.create(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        tokenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteByEmail(
            @PathVariable String email,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        tokenService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
