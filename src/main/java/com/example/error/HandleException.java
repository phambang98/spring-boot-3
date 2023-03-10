package com.example.error;

import com.example.model.FieldError;
import com.example.model.RecordNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {
    Logger logger = LoggerFactory.getLogger(HandleException.class);

    @ExceptionHandler({RecordNotFoundException.class, JobExecutionException.class})
    public ResponseEntity<FieldError> handleRecordNotFoundException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(new FieldError(ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FieldError> handleException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(new FieldError(ex.getMessage(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
