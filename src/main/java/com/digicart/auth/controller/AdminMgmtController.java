package com.digicart.auth.controller;

import com.digicart.auth.dto.AdminCreateRequest;
import com.digicart.auth.dto.ChangePasswordRequest;
import com.digicart.auth.entity.Role;
import com.digicart.auth.service.AdminMgmtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for platform admin management operations.
 */
@RestController
@RequestMapping("/api/auth/admin-mgmt")
public class AdminMgmtController {

    private final AdminMgmtService service;

    public AdminMgmtController(AdminMgmtService service) {
        this.service = service;
    }

    // --- Merchant admins ---

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listAdmins() {
        return ResponseEntity.ok(service.listByRole(Role.merchant).stream().map(AdminMgmtService::safe).toList());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAdmin(@Valid @RequestBody AdminCreateRequest req) {
        return ResponseEntity.ok(AdminMgmtService.safe(service.createUser(req, Role.merchant)));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<Map<String, Object>> setBlock(@PathVariable String id, @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(AdminMgmtService.safe(service.setBlocked(id, Boolean.TRUE.equals(body.get("blocked")))));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> resetPassword(@PathVariable String id, @RequestBody Map<String, String> body) {
        service.resetPassword(id, body.get("password"));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Superadmins ---

    @GetMapping("/superadmins")
    public ResponseEntity<List<Map<String, Object>>> listSuperadmins() {
        return ResponseEntity.ok(service.listByRole(Role.superadmin).stream().map(AdminMgmtService::safe).toList());
    }

    @PostMapping("/superadmin")
    public ResponseEntity<Map<String, Object>> createSuperadmin(@Valid @RequestBody AdminCreateRequest req) {
        return ResponseEntity.ok(AdminMgmtService.safe(service.createUser(req, Role.superadmin)));
    }

    @DeleteMapping("/superadmin/{id}")
    public ResponseEntity<Void> deleteSuperadmin(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Customers ---

    @GetMapping("/customers")
    public ResponseEntity<List<Map<String, Object>>> listCustomers() {
        return ResponseEntity.ok(service.listByRole(Role.user).stream().map(AdminMgmtService::safe).toList());
    }

    @PatchMapping("/customers/{id}/status")
    public ResponseEntity<Map<String, Object>> setCustomerStatus(@PathVariable String id, @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(AdminMgmtService.safe(service.setBlocked(id, Boolean.TRUE.equals(body.get("blocked")))));
    }

    // --- Self-service ---

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody ChangePasswordRequest req) {
        service.changeOwnPassword(userId, req);
        return ResponseEntity.noContent().build();
    }
}
