package com.example.springchat.error;

import com.example.springcore.model.FieldError;
import com.example.springcore.model.RecordNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class HandleException {
    Logger logger = LoggerFactory.getLogger(HandleException.class);

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<FieldError> handleRecordNotFoundException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(new FieldError(ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<FieldError> handleAuthenticationException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(new FieldError(ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FieldError> handleException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(new FieldError(ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
