package com.digicart.auth.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Password Reset Token).
 */
@Entity
@Table(name = "password_reset_tokens", schema = "auth_svc")
@EntityListeners(AuditingEntityListener.class)
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Creates a new {@code PasswordResetToken}.
     */
    public PasswordResetToken() {}
    /**
     * Returns id.
     * @return the string
     */
    public String getId() { return id; }
    /**
     * Sets id.
     *
     * @param id resource identifier
     */
    public void setId(String id) { this.id = id; }
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
    /**
     * Returns created at.
     * @return the instant
     */
    public Instant getCreatedAt() { return createdAt; }
    /**
     * Sets created at.
     *
     * @param createdAt created at
     */
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
