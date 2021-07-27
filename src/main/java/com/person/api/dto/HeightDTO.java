package com.person.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class HeightDTO {

    @NotBlank
    private String height;

    public HeightDTO(@NotBlank @Valid String height) {
        this.height = height;
    }

    public HeightDTO() {
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}
