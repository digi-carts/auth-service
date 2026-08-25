package com.digicart.auth;

import com.digicart.auth.entity.Role;
import com.digicart.auth.entity.User;
import com.digicart.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;

    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        seedUser("E2E_SUPERADMIN_EMAIL", "E2E_SUPERADMIN_PASSWORD", Role.superadmin, "COMPLETED");
        seedUser("E2E_ADMIN_EMAIL", "E2E_ADMIN_PASSWORD", Role.merchant, "COMPLETED");
    }

    private void seedUser(String emailEnv, String passwordEnv, Role role, String setupStatus) {
        String email = System.getenv(emailEnv);
        String password = System.getenv(passwordEnv);
        if (email == null || email.isBlank() || password == null || password.isBlank()) return;

        if (userRepository.existsByEmail(email)) {
            userRepository.findByEmail(email).ifPresent(u -> {
                u.setPasswordHash(encoder.encode(password));
                u.setRole(role);
                u.setSetupStatus(setupStatus);
                u.setBlocked(false);
                userRepository.save(u);
            });
            log.info("DataSeeder: updated {}", email);
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        user.setRole(role);
        user.setProvider("credentials");
        user.setSetupStatus(setupStatus);
        user.setSetupWizardPage(0);
        user.setBlocked(false);
        userRepository.save(user);
        log.info("DataSeeder: created {}", email);
    }
}
