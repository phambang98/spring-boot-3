package com.example.core.error;

public class BadRequestException extends Exception{

    public BadRequestException(String msg) {
        super(msg);
    }
}
