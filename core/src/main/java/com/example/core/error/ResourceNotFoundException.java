package com.example.core.error;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
