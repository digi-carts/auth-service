package com.digicart.auth.entity;

/**
 * Platform roles carried in JWT {@code role} claims and {@code users.role}.
 */
public enum Role {
    superadmin,
    merchant,
    user
}
