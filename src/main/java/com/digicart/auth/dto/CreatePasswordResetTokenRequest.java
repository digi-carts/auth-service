package com.digicart.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

/**
 * Request/response DTO: Create Password Reset Token Request.
 */
public class CreatePasswordResetTokenRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String token;
    private Instant expiresAt;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
