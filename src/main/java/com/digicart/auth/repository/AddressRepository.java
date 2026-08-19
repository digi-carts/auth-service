package com.digicart.auth.repository;

import com.digicart.auth.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for address  persistence.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    /**
     * Finds by user id.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @return matching records
     */
    List<Address> findByUserId(String userId);
    /**
     * Finds by user id and is default.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param isDefault is default
     * @return matching records
     */
    List<Address> findByUserIdAndIsDefault(String userId, Boolean isDefault);
}
