package com.digicart.auth.service;

import com.digicart.auth.dto.CreatePasswordResetTokenRequest;
import com.digicart.auth.entity.PasswordResetToken;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetTokenService(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<PasswordResetToken> findAll() {
        return tokenRepository.findAll();
    }

    public PasswordResetToken findById(String id) {
        return tokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PasswordResetToken not found: " + id));
    }

    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
    }

    @Transactional
    public PasswordResetToken create(CreatePasswordResetTokenRequest req) {
        PasswordResetToken prt = new PasswordResetToken();
        prt.setEmail(req.getEmail());
        prt.setToken(req.getToken());
        prt.setExpiresAt(req.getExpiresAt());
        return tokenRepository.save(prt);
    }

    @Transactional
    public void deleteByEmail(String email) {
        tokenRepository.deleteByEmail(email);
    }

    @Transactional
    public void delete(String id) {
        if (!tokenRepository.existsById(id)) {
            throw new EntityNotFoundException("PasswordResetToken not found: " + id);
        }
        tokenRepository.deleteById(id);
    }
}
