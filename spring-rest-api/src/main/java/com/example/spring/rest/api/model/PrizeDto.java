package com.example.spring.rest.api.model;

import lombok.Data;

@Data
public class PrizeDto {
    private Long id;
    private Long prizeGroupId;
    private String description;
    private String imageUrl;
    private String type;
    private Long displayNumber;
    private Long quantity;

    public PrizeDto() {
    }

    public PrizeDto(Long id, Long prizeGroupId, String description, String imageUrl, String type, Long displayNumber, Long quantity) {
        this.id = id;
        this.prizeGroupId = prizeGroupId;
        this.description = description;
        this.imageUrl = imageUrl;
        this.type = type;
        this.displayNumber = displayNumber;
        this.quantity = quantity;
    }
}
