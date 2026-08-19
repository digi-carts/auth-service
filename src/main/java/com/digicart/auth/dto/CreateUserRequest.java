package com.digicart.auth.dto;

import com.digicart.auth.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request/response DTO: Create User Request.
 */
public class CreateUserRequest {

    @NotBlank
    @Email
    private String email;
    private String passwordHash;
    private String name;
    private String phone;
    private String provider;
    private String providerAccountId;
    private Role role;
    private String storeId;
    private String subscriptionId;

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
}
