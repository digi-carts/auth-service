package com.digicart.auth.dto;

import com.digicart.auth.entity.Role;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getStoreId() { return storeId; }
    public void setStoreId(String storeId) { this.storeId = storeId; }
    public String getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
    public Boolean getBlocked() { return blocked; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }
    public String getSetupStatus() { return setupStatus; }
    public void setSetupStatus(String setupStatus) { this.setupStatus = setupStatus; }
    public Integer getSetupWizardPage() { return setupWizardPage; }
    public void setSetupWizardPage(Integer setupWizardPage) { this.setupWizardPage = setupWizardPage; }
}
