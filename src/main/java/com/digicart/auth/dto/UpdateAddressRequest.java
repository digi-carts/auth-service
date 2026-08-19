package com.digicart.auth.dto;

public class UpdateAddressRequest {
    private String name;
    private String line1;
    private String city;
    private String country;
    private String zip;
    private Boolean isDefault;

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
