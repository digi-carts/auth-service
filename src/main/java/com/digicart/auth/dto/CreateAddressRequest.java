package com.digicart.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateAddressRequest {

    @NotBlank
    private String userId;
    @NotBlank
    private String name;
    @NotBlank
    private String line1;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String zip;
    private Boolean isDefault = false;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLine1() { return line1; }
    public void setLine1(String line1) { this.line1 = line1; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
    public Boolean getIsDefault() { return isDefault; }
    public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }
}
