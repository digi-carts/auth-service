package com.digicart.auth.dto;

import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;

import java.time.Instant;
import java.util.UUID;

public record UserDto(
    UUID id,
    String email,
    String name,
    String phone,
    String provider,
    Role role,
    String storeId,
    String subscriptionId,
    Boolean blocked,
    Instant lastLoginAt,
    String setupStatus,
    Integer setupWizardPage,
    Instant createdAt,
    Instant updatedAt
) {
    public static UserDto from(User u) {
        return new UserDto(
            u.getId(), u.getEmail(), u.getName(), u.getPhone(), u.getProvider(),
            u.getRole(), u.getStoreId(), u.getSubscriptionId(), u.getBlocked(),
            u.getLastLoginAt(), u.getSetupStatus(), u.getSetupWizardPage(),
            u.getCreatedAt(), u.getUpdatedAt()
        );
    }
}
