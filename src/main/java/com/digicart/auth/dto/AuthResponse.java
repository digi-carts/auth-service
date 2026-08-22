package com.digicart.auth.dto;

import com.digicart.auth.entity.User;

public record AuthResponse(User user, String accessToken, String refreshToken) {}
