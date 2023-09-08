package com.example.spring.rest.api.model;

import lombok.Data;

@Data
public class HorseRacingDto {
    private Long id;

    private Long horseRacingGroupId;

    private String description;

    private Long displayNumber;

    private String imageUrl;

    public HorseRacingDto() {
    }

    public HorseRacingDto(Long id, Long horseRacingGroupId, String description, Long displayNumber, String imageUrl) {
        this.id = id;
        this.horseRacingGroupId = horseRacingGroupId;
        this.description = description;
        this.displayNumber = displayNumber;
        this.imageUrl = imageUrl;
    }
}
