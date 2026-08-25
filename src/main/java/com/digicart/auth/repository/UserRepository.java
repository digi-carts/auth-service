package com.digicart.auth.repository;

import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for user  persistence.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderAndProviderAccountId(String provider, String providerAccountId);
    List<User> findByStoreId(String storeId);
    List<User> findByRole(Role role);
    long countByRole(Role role);
    long countByRoleAndBlocked(Role role, boolean blocked);
    boolean existsByEmail(String email);
}
