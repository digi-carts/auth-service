package com.digicart.auth.service;

import com.digicart.auth.dto.CreateAddressRequest;
import com.digicart.auth.dto.UpdateAddressRequest;
import com.digicart.auth.entity.Address;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application service implementing address use cases for <em>auth-service</em>.
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    /**
     * Creates a new {@code AddressService}.
     *
     * @param addressRepository address repository collaborator
     */
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Finds all.
     * @return matching records
     */
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    /**
     * Finds by id.
     *
     * @param id resource identifier
     * @return the address
     */
    public Address findById(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + id));
    }

    /**
     * Finds by user id.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @return matching records
     */
    public List<Address> findByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    /**
     * Creates a new record.
     *
     * @param req request payload
     * @return the address
     */
    @Transactional
    public Address create(CreateAddressRequest req) {
        Address address = new Address();
        address.setUserId(req.getUserId());
        address.setName(req.getName());
        address.setLine1(req.getLine1());
        address.setCity(req.getCity());
        address.setCountry(req.getCountry());
        address.setZip(req.getZip());
        if (req.getIsDefault() != null) address.setIsDefault(req.getIsDefault());
        return addressRepository.save(address);
    }

    /**
     * Updates an existing record.
     *
     * @param id resource identifier
     * @param req request payload
     * @return the address
     */
    @Transactional
    public Address update(String id, UpdateAddressRequest req) {
        Address address = findById(id);
        if (req.getName() != null) address.setName(req.getName());
        if (req.getLine1() != null) address.setLine1(req.getLine1());
        if (req.getCity() != null) address.setCity(req.getCity());
        if (req.getCountry() != null) address.setCountry(req.getCountry());
        if (req.getZip() != null) address.setZip(req.getZip());
        if (req.getIsDefault() != null) address.setIsDefault(req.getIsDefault());
        return addressRepository.save(address);
    }

    /**
     * Deletes the record.
     *
     * @param id resource identifier
     */
    @Transactional
    public void delete(String id) {
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException("Address not found: " + id);
        }
        addressRepository.deleteById(id);
    }
}
