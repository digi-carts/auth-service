package com.digicart.auth.repository;

import com.digicart.auth.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for password reset token  persistence.
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    /**
     * Finds by token.
     *
     * @param token token value
     * @return the value if present
     */
    Optional<PasswordResetToken> findByToken(String token);
    /**
     * Finds by email.
     *
     * @param email email address
     * @return the value if present
     */
    Optional<PasswordResetToken> findByEmail(String email);
    /**
     * Delete by email.
     *
     * @param email email address
     */
    void deleteByEmail(String email);
}
