package com.digicart.auth.dto;

import com.digicart.auth.entity.Role;

/**
 * Request/response DTO: Update User Request.
 */
public class UpdateUserRequest {
    private String name;
    private String phone;
    private String passwordHash;
    private Role role;
    private String storeId;
    private String subscriptionId;
    private Boolean blocked;
    private String setupStatus;
    private Integer setupWizardPage;

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
}
