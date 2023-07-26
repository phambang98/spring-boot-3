package com.example.spring.rest.api.error;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class IncorrectUserNameException extends UsernameNotFoundException {
    public IncorrectUserNameException(String msg) {
        super(msg);
    }
}
