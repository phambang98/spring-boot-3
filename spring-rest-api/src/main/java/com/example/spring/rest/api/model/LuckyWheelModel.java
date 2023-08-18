package com.example.spring.rest.api.model;

import lombok.Data;

@Data
public class LuckyWheelModel {
    private Long luckyNumber;
    private Long spinNumber;
    private Long prizeId;
    private String message;
}
