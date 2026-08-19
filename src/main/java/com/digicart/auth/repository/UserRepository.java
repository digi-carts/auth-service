package com.digicart.auth.repository;

import com.digicart.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for user  persistence.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Finds by email.
     *
     * @param email email address
     * @return the value if present
     */
    Optional<User> findByEmail(String email);
    /**
     * Finds by store id.
     *
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    List<User> findByStoreId(String storeId);
    /**
     * Returns whether by email exists.
     *
     * @param email email address
     * @return the boolean
     */
    boolean existsByEmail(String email);
}
