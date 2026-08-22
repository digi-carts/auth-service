package com.digicart.auth.service;

import com.digicart.auth.dto.CreateUserRequest;
import com.digicart.auth.dto.UpdateUserRequest;
import com.digicart.auth.entity.User;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    public List<User> findByStoreId(String storeId) {
        return userRepository.findByStoreId(storeId);
    }

    @Transactional
    public User create(CreateUserRequest req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPasswordHash(req.getPasswordHash());
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setProvider(req.getProvider());
        user.setProviderAccountId(req.getProviderAccountId());
        if (req.getRole() != null) user.setRole(req.getRole());
        user.setStoreId(req.getStoreId());
        user.setSubscriptionId(req.getSubscriptionId());
        return userRepository.save(user);
    }

    @Transactional
    public User update(String id, UpdateUserRequest req) {
        User user = findById(id);
        if (req.getName() != null) user.setName(req.getName());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getPasswordHash() != null) user.setPasswordHash(req.getPasswordHash());
        if (req.getRole() != null) user.setRole(req.getRole());
        if (req.getStoreId() != null) user.setStoreId(req.getStoreId());
        if (req.getSubscriptionId() != null) user.setSubscriptionId(req.getSubscriptionId());
        if (req.getBlocked() != null) user.setBlocked(req.getBlocked());
        if (req.getSetupStatus() != null) user.setSetupStatus(req.getSetupStatus());
        if (req.getSetupWizardPage() != null) user.setSetupWizardPage(req.getSetupWizardPage());
        return userRepository.save(user);
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!userRepository.existsById(uuid)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(uuid);
    }
}
