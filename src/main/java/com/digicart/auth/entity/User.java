package com.digicart.auth.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (User).
 */
@Entity
@Table(name = "users", schema = "auth_svc")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_account_id")
    private String providerAccountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.user;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "blocked", nullable = false)
    private Boolean blocked = false;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @Column(name = "setup_status", nullable = false)
    private String setupStatus = "CREATED";

    @Column(name = "setup_wizard_page", nullable = false)
    private Integer setupWizardPage = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Creates a new {@code User}.
     */
    public User() {}
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
     * Returns password hash.
     * @return the string
     */
    public String getPasswordHash() { return passwordHash; }
    /**
     * Sets password hash.
     *
     * @param passwordHash password hash
     */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    /**
     * Returns name.
     * @return the string
     */
    public String getName() { return name; }
    /**
     * Sets name.
     *
     * @param name name
     */
    public void setName(String name) { this.name = name; }
    /**
     * Returns phone.
     * @return the string
     */
    public String getPhone() { return phone; }
    /**
     * Sets phone.
     *
     * @param phone phone
     */
    public void setPhone(String phone) { this.phone = phone; }
    /**
     * Returns provider.
     * @return the string
     */
    public String getProvider() { return provider; }
    /**
     * Sets provider.
     *
     * @param provider provider
     */
    public void setProvider(String provider) { this.provider = provider; }
    /**
     * Returns provider account id.
     * @return the string
     */
    public String getProviderAccountId() { return providerAccountId; }
    /**
     * Sets provider account id.
     *
     * @param providerAccountId provider account id
     */
    public void setProviderAccountId(String providerAccountId) { this.providerAccountId = providerAccountId; }
    /**
     * Returns role.
     * @return the role
     */
    public Role getRole() { return role; }
    /**
     * Sets role.
     *
     * @param role caller role
     */
    public void setRole(Role role) { this.role = role; }
    /**
     * Returns store id.
     * @return the string
     */
    public String getStoreId() { return storeId; }
    /**
     * Sets store id.
     *
     * @param storeId store (tenant) identifier
     */
    public void setStoreId(String storeId) { this.storeId = storeId; }
    /**
     * Returns subscription id.
     * @return the string
     */
    public String getSubscriptionId() { return subscriptionId; }
    /**
     * Sets subscription id.
     *
     * @param subscriptionId subscription id
     */
    public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
    /**
     * Returns blocked.
     * @return the boolean
     */
    public Boolean getBlocked() { return blocked; }
    /**
     * Sets blocked.
     *
     * @param blocked blocked
     */
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }
    /**
     * Returns last login at.
     * @return the instant
     */
    public Instant getLastLoginAt() { return lastLoginAt; }
    /**
     * Sets last login at.
     *
     * @param lastLoginAt last login at
     */
    public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    /**
     * Returns setup status.
     * @return the string
     */
    public String getSetupStatus() { return setupStatus; }
    /**
     * Sets setup status.
     *
     * @param setupStatus setup status
     */
    public void setSetupStatus(String setupStatus) { this.setupStatus = setupStatus; }
    /**
     * Returns setup wizard page.
     * @return the integer
     */
    public Integer getSetupWizardPage() { return setupWizardPage; }
    /**
     * Sets setup wizard page.
     *
     * @param setupWizardPage setup wizard page
     */
    public void setSetupWizardPage(Integer setupWizardPage) { this.setupWizardPage = setupWizardPage; }
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
    /**
     * Returns updated at.
     * @return the instant
     */
    public Instant getUpdatedAt() { return updatedAt; }
    /**
     * Sets updated at.
     *
     * @param updatedAt updated at
     */
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
