package com.example.spring.rest.api.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PrizeGroupDto {
    private Long id;
    private String description;
    private Date dateTime;
    private List<PrizeDto> prizeList;

    public PrizeGroupDto() {
    }

    public PrizeGroupDto(Long id, String description, Date dateTime) {
        this.id = id;
        this.description = description;
        this.dateTime = dateTime;
    }
}
