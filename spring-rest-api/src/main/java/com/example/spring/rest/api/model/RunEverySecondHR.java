package com.example.spring.rest.api.model;

import lombok.Data;

@Data
public class RunEverySecondHR {
    private int seconds;
    private String type;
    private long betHrOne;
    private long betHrTwo;
    private long betHrThree;
    private long betHrFour;

    public RunEverySecondHR(int seconds, String type, long betHrOne, long betHrTwo, long betHrThree, long betHrFour) {
        this.seconds = seconds;
        this.type = type;
        this.betHrOne = betHrOne;
        this.betHrTwo = betHrTwo;
        this.betHrThree = betHrThree;
        this.betHrFour = betHrFour;
    }
}
