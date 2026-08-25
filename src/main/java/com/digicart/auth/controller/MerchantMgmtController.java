package com.digicart.auth.controller;

import com.digicart.auth.dto.ChangePasswordRequest;
import com.digicart.auth.service.AdminMgmtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for merchant self-service management operations.
 */
@RestController
@RequestMapping("/api/auth/merchant-mgmt")
public class MerchantMgmtController {

    private final AdminMgmtService service;

    public MerchantMgmtController(AdminMgmtService service) {
        this.service = service;
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody ChangePasswordRequest req) {
        service.changeOwnPassword(userId, req);
        return ResponseEntity.noContent().build();
    }
}
