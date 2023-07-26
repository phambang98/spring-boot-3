package com.example.core.error;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
