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
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByEmail(String email);
    void deleteByEmail(String email);
}
