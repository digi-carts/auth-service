package com.digicart.auth.service;

import com.digicart.auth.dto.CreateAddressRequest;
import com.digicart.auth.dto.UpdateAddressRequest;
import com.digicart.auth.entity.Address;
import com.digicart.auth.exception.EntityNotFoundException;
import com.digicart.auth.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(String id) {
        return addressRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + id));
    }

    public List<Address> findByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

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

    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (!addressRepository.existsById(uuid)) {
            throw new EntityNotFoundException("Address not found: " + id);
        }
        addressRepository.deleteById(uuid);
    }
}
