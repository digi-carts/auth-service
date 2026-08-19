package com.digicart.auth.dto;

/**
 * Request/response DTO: Update Address Request.
 */
public class UpdateAddressRequest {
    private String name;
    private String line1;
    private String city;
    private String country;
    private String zip;
    private Boolean isDefault;

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
}
