package com.person.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class WeightDTO {

    @NotBlank
    private String weight;

    public WeightDTO(@NotBlank @Valid String weight) {
        this.weight = weight;
    }

    public WeightDTO() {
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
