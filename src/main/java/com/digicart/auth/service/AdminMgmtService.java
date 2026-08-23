package com.digicart.auth.service;

import com.digicart.auth.dto.AdminCreateRequest;
import com.digicart.auth.dto.ChangePasswordRequest;
import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import com.digicart.auth.exception.BadCredentialsException;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Admin management: CRUD for merchant admins, superadmins, customers, and self-service password change.
 */
@Service
public class AdminMgmtService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminMgmtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Transactional
    public User createUser(AdminCreateRequest req, Role role) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + req.getEmail());
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setName(req.getName());
        user.setRole(role);
        return userRepository.save(user);
    }

    @Transactional
    public User setBlocked(String id, boolean blocked) {
        User user = findById(id);
        user.setBlocked(blocked);
        return userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String id, String newPassword) {
        User user = findById(id);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void changeOwnPassword(String userId, ChangePasswordRequest req) {
        User user = findById(userId);
        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException();
        }
        user.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!userRepository.existsById(uuid)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(uuid);
    }

    public static Map<String, Object> safe(User u) {
        return Map.of(
                "id", u.getId().toString(),
                "email", u.getEmail(),
                "name", u.getName() != null ? u.getName() : "",
                "role", u.getRole().name(),
                "blocked", u.getBlocked(),
                "createdAt", u.getCreatedAt() != null ? u.getCreatedAt().toString() : "",
                "storeId", u.getStoreId() != null ? u.getStoreId() : ""
        );
    }

    private User findById(String id) {
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }
}
