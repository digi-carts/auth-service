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

    /**
     * Creates a new {@code PasswordResetTokenController}.
     *
     * @param tokenService token service collaborator
     */
    public PasswordResetTokenController(PasswordResetTokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Handles {@code GET /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the password reset token
     */
    @GetMapping("/{id}")
    public PasswordResetToken findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return tokenService.findById(id);
    }

    /**
     * Handles {@code GET /token/{token}}.
     *
     * @param token token value
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the password reset token
     */
    @GetMapping("/token/{token}")
    public PasswordResetToken findByToken(
            @PathVariable String token,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return tokenService.findByToken(token);
    }

    /**
     * Handles POST.
     *
     * @param req request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @PostMapping
    public ResponseEntity<PasswordResetToken> create(
            @Valid @RequestBody CreatePasswordResetTokenRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.create(req));
    }

    /**
     * Handles {@code DELETE /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        tokenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles {@code DELETE /email/{email}}.
     *
     * @param email email address
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteByEmail(
            @PathVariable String email,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        tokenService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
