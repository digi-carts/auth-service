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

    /**
     * Returns email.
     * @return the string
     */
    public String getEmail() { return email; }
    /**
     * Sets email.
     *
     * @param email email address
     */
    public void setEmail(String email) { this.email = email; }
    /**
     * Returns token.
     * @return the string
     */
    public String getToken() { return token; }
    /**
     * Sets token.
     *
     * @param token token value
     */
    public void setToken(String token) { this.token = token; }
    /**
     * Returns expires at.
     * @return the instant
     */
    public Instant getExpiresAt() { return expiresAt; }
    /**
     * Sets expires at.
     *
     * @param expiresAt expires at
     */
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
