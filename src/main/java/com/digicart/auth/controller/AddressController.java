package com.digicart.auth.controller;

import com.digicart.auth.dto.CreateAddressRequest;
import com.digicart.auth.dto.UpdateAddressRequest;
import com.digicart.auth.entity.Address;
import com.digicart.auth.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing address HTTP APIs for <em>auth-service</em>.
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> findAll(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestParam(required = false) String userId2) {
        if (userId2 != null) {
            return addressService.findByUserId(userId2);
        }
        return addressService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Address> findByUserId(
            @PathVariable String userId,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return addressService.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public Address findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return addressService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Address> create(
            @Valid @RequestBody CreateAddressRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(req));
    }

    @PatchMapping("/{id}")
    public Address update(
            @PathVariable String id,
            @RequestBody UpdateAddressRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return addressService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
