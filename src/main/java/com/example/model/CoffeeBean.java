package com.example.model;

import jakarta.validation.constraints.Size;

public class CoffeeBean extends BaseBeanBatch {

    @Size(max = 8, message = "max length")
    private String brand;
    private String origin;
    private String characteristics;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }
}
