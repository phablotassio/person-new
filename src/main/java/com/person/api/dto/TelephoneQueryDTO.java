package com.person.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TelephoneQueryDTO extends TelephoneDTO {

    private Long id;

    public TelephoneQueryDTO(@NotBlank String number, @NotNull Long areaCode, Long id) {
        super(number, areaCode);
        this.id = id;
    }

    public TelephoneQueryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
