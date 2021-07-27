package com.person.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddressQueryDTO extends AddressDTO {

    private Long id;

    public AddressQueryDTO(@NotBlank @Size(min = 8, max = 11) String zipCode, @NotBlank @Size(max = 40) String city, @NotBlank @Size(max = 70) String street, @Size(max = 70) String neighborhood, @NotBlank @Size(min = 2, max = 30) String state, @Size(max = 70) String complement, Long id) {
        super(zipCode, city, street, neighborhood, state, complement);
        this.id = id;
    }

    public AddressQueryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
