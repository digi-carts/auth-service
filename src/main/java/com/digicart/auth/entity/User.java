package com.digicart.auth.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "auth_svc")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    public User() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getProviderAccountId() { return providerAccountId; }
    public void setProviderAccountId(String providerAccountId) { this.providerAccountId = providerAccountId; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getStoreId() { return storeId; }
    public void setStoreId(String storeId) { this.storeId = storeId; }
    public String getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
    public Boolean getBlocked() { return blocked; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }
    public Instant getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public String getSetupStatus() { return setupStatus; }
    public void setSetupStatus(String setupStatus) { this.setupStatus = setupStatus; }
    public Integer getSetupWizardPage() { return setupWizardPage; }
    public void setSetupWizardPage(Integer setupWizardPage) { this.setupWizardPage = setupWizardPage; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
