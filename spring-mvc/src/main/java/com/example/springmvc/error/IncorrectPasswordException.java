package com.example.springmvc.error;

import org.springframework.security.core.AuthenticationException;

public class IncorrectPasswordException extends AuthenticationException {
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
