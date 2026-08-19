package com.digicart.auth.service;

import com.digicart.auth.dto.CreatePasswordResetTokenRequest;
import com.digicart.auth.entity.PasswordResetToken;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application service implementing password reset token use cases for <em>auth-service</em>.
 */
@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    /**
     * Creates a new {@code PasswordResetTokenService}.
     *
     * @param tokenRepository token repository collaborator
     */
    public PasswordResetTokenService(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * Finds all.
     * @return matching records
     */
    public List<PasswordResetToken> findAll() {
        return tokenRepository.findAll();
    }

    /**
     * Finds by id.
     *
     * @param id resource identifier
     * @return the password reset token
     */
    public PasswordResetToken findById(String id) {
        return tokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PasswordResetToken not found: " + id));
    }

    /**
     * Finds by token.
     *
     * @param token token value
     * @return the password reset token
     */
    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
    }

    /**
     * Creates a new record.
     *
     * @param req request payload
     * @return the password reset token
     */
    @Transactional
    public PasswordResetToken create(CreatePasswordResetTokenRequest req) {
        PasswordResetToken prt = new PasswordResetToken();
        prt.setEmail(req.getEmail());
        prt.setToken(req.getToken());
        prt.setExpiresAt(req.getExpiresAt());
        return tokenRepository.save(prt);
    }

    /**
     * Delete by email.
     *
     * @param email email address
     */
    @Transactional
    public void deleteByEmail(String email) {
        tokenRepository.deleteByEmail(email);
    }

    /**
     * Deletes the record.
     *
     * @param id resource identifier
     */
    @Transactional
    public void delete(String id) {
        if (!tokenRepository.existsById(id)) {
            throw new EntityNotFoundException("PasswordResetToken not found: " + id);
        }
        tokenRepository.deleteById(id);
    }
}
