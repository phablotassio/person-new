package com.person.api.dto;

public class WeightAndHeightDTO {

    private WeightDTO weightDTO;
    private HeightDTO heightDTO;

    public WeightAndHeightDTO(WeightDTO weightDTO, HeightDTO heightDTO) {
        this.weightDTO = weightDTO;
        this.heightDTO = heightDTO;
    }

    public WeightAndHeightDTO(String weight, String height) {
        this.weightDTO = new WeightDTO(weight);
        this.heightDTO = new HeightDTO(height);
    }

    public WeightAndHeightDTO() {
    }

    public WeightDTO getWeightDTO() {
        return weightDTO;
    }

    public void setWeightDTO(WeightDTO weightDTO) {
        this.weightDTO = weightDTO;
    }

    public HeightDTO getHeightDTO() {
        return heightDTO;
    }

    public void setHeightDTO(HeightDTO heightDTO) {
        this.heightDTO = heightDTO;
    }
}
