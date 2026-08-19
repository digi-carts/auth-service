package com.digicart.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Spring Boot entry point for the <em>auth-service</em> microservice.
 */
@SpringBootApplication
@EnableJpaAuditing
public class AuthServiceApplication {
    /**
     * Spring Boot process entry point.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
