package com.example.springapi.error;

import com.example.springcore.model.FieldError;
import com.example.springcore.model.RecordNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {
    Logger logger = LoggerFactory.getLogger(HandleException.class);

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<FieldError> handleRecordNotFoundException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(new FieldError(ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FieldError> handleException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(new FieldError(ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
