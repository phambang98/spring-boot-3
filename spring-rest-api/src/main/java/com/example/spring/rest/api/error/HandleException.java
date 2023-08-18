package com.example.spring.rest.api.error;

import com.example.core.model.RecordNotFoundException;
import com.example.core.model.ResultData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<ResultData> handleRecordNotFoundException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(ResultData.builder().success(false).message(ex.getMessage() + "|" + ex.getLocalizedMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ResultData> handleAuthenticationException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(ResultData.builder().success(false).message(ex.getMessage() + "|" + ex.getLocalizedMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultData> handleException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(ResultData.builder().success(false).message(ex.getMessage() + "|" + ex.getLocalizedMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResultData> handleTokenRefreshException(HttpServletRequest request, TokenRefreshException ex) {
        return new ResponseEntity<>(ResultData.builder().success(false).message(ex.getMessage() + "|" + ex.getLocalizedMessage()).build(), HttpStatus.FORBIDDEN);
    }
}
