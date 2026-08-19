package com.digicart.auth.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Address).
 */
@Entity
@Table(name = "addresses", schema = "auth_svc")
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "line1", nullable = false)
    private String line1;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zip", nullable = false)
    private String zip;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Creates a new {@code Address}.
     */
    public Address() {}
    /**
     * Returns id.
     * @return the string
     */
    public String getId() { return id; }
    /**
     * Sets id.
     *
     * @param id resource identifier
     */
    public void setId(String id) { this.id = id; }
    /**
     * Returns user id.
     * @return the string
     */
    public String getUserId() { return userId; }
    /**
     * Sets user id.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     */
    public void setUserId(String userId) { this.userId = userId; }
    /**
     * Returns name.
     * @return the string
     */
    public String getName() { return name; }
    /**
     * Sets name.
     *
     * @param name name
     */
    public void setName(String name) { this.name = name; }
    /**
     * Returns line1.
     * @return the string
     */
    public String getLine1() { return line1; }
    /**
     * Sets line1.
     *
     * @param line1 line1
     */
    public void setLine1(String line1) { this.line1 = line1; }
    /**
     * Returns city.
     * @return the string
     */
    public String getCity() { return city; }
    /**
     * Sets city.
     *
     * @param city city
     */
    public void setCity(String city) { this.city = city; }
    /**
     * Returns country.
     * @return the string
     */
    public String getCountry() { return country; }
    /**
     * Sets country.
     *
     * @param country country
     */
    public void setCountry(String country) { this.country = country; }
    /**
     * Returns zip.
     * @return the string
     */
    public String getZip() { return zip; }
    /**
     * Sets zip.
     *
     * @param zip zip
     */
    public void setZip(String zip) { this.zip = zip; }
    /**
     * Returns is default.
     * @return the boolean
     */
    public Boolean getIsDefault() { return isDefault; }
    /**
     * Sets is default.
     *
     * @param isDefault is default
     */
    public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }
    /**
     * Returns created at.
     * @return the instant
     */
    public Instant getCreatedAt() { return createdAt; }
    /**
     * Sets created at.
     *
     * @param createdAt created at
     */
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
