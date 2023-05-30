package com.example.springchat.error;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
