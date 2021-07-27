package com.person.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    @NotBlank
    @Size(min = 8, max = 11)
    private String zipCode;
    @NotBlank
    @Size(max = 40)
    private String city;
    @NotBlank
    @Size(max = 70)
    private String street;
    @Size(max = 70)
    private String neighborhood;
    @NotBlank
    @Size(min = 2, max = 30)
    private String state;
    @Size(max = 70)
    private String complement;

    public AddressDTO(@NotBlank @Size(min = 8, max = 11) String zipCode, @NotBlank @Size(max = 40) String city, @NotBlank @Size(max = 70) String street, @Size(max = 70) String neighborhood, @NotBlank @Size(min = 2, max = 30) String state, @Size(max = 70) String complement) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.neighborhood = neighborhood;
        this.state = state;
        this.complement = complement;
    }

    public AddressDTO() {
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
