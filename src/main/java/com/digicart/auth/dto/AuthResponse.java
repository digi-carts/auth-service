package com.digicart.auth.dto;

public record AuthResponse(UserDto user, String accessToken, String refreshToken) {}
