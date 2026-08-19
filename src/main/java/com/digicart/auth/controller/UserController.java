package com.digicart.auth.controller;

import com.digicart.auth.dto.CreateUserRequest;
import com.digicart.auth.dto.UpdateUserRequest;
import com.digicart.auth.entity.User;
import com.digicart.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing user HTTP APIs for <em>auth-service</em>.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Creates a new {@code UserController}.
     *
     * @param userService user service collaborator
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    @GetMapping
    public List<User> findAll(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestParam(required = false) String storeId) {
        if (storeId != null) {
            return userService.findByStoreId(storeId);
        }
        return userService.findAll();
    }

    /**
     * Handles {@code GET /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the user
     */
    @GetMapping("/{id}")
    public User findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return userService.findById(id);
    }

    /**
     * Handles {@code GET /email/{email}}.
     *
     * @param email email address
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the user
     */
    @GetMapping("/email/{email}")
    public User findByEmail(
            @PathVariable String email,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return userService.findByEmail(email);
    }

    /**
     * Handles POST.
     *
     * @param req request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @PostMapping
    public ResponseEntity<User> create(
            @Valid @RequestBody CreateUserRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(req));
    }

    /**
     * Handles {@code PATCH /{id}}.
     *
     * @param id resource identifier
     * @param req request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the user
     */
    @PatchMapping("/{id}")
    public User update(
            @PathVariable String id,
            @RequestBody UpdateUserRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return userService.update(id, req);
    }

    /**
     * Handles {@code DELETE /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
