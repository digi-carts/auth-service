package com.digicart.auth.service;

import com.digicart.auth.dto.CreateUserRequest;
import com.digicart.auth.dto.UpdateUserRequest;
import com.digicart.auth.entity.User;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application service implementing user use cases for <em>auth-service</em>.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Creates a new {@code UserService}.
     *
     * @param userRepository user repository collaborator
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds all.
     * @return matching records
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Finds by id.
     *
     * @param id resource identifier
     * @return the user
     */
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    /**
     * Finds by email.
     *
     * @param email email address
     * @return the user
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    /**
     * Finds by store id.
     *
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    public List<User> findByStoreId(String storeId) {
        return userRepository.findByStoreId(storeId);
    }

    /**
     * Creates a new record.
     *
     * @param req request payload
     * @return the user
     */
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

    /**
     * Updates an existing record.
     *
     * @param id resource identifier
     * @param req request payload
     * @return the user
     */
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

    /**
     * Deletes the record.
     *
     * @param id resource identifier
     */
    @Transactional
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
