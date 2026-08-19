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
}
