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
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    /**
     * Creates a new {@code AddressController}.
     *
     * @param addressService address service collaborator
     */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Handles GET.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @param userId2 user id2
     * @return matching records
     */
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

    /**
     * Handles {@code GET /user/{userId}}.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param requestUserId request user id
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return matching records
     */
    @GetMapping("/user/{userId}")
    public List<Address> findByUserId(
            @PathVariable String userId,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return addressService.findByUserId(userId);
    }

    /**
     * Handles {@code GET /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the address
     */
    @GetMapping("/{id}")
    public Address findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return addressService.findById(id);
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
    public ResponseEntity<Address> create(
            @Valid @RequestBody CreateAddressRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(req));
    }

    /**
     * Handles {@code PATCH /{id}}.
     *
     * @param id resource identifier
     * @param req request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the address
     */
    @PatchMapping("/{id}")
    public Address update(
            @PathVariable String id,
            @RequestBody UpdateAddressRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return addressService.update(id, req);
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
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
