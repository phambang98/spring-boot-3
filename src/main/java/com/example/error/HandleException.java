package com.example.error;

import com.example.model.FieldError;
import com.example.model.RecordNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<FieldError> handleRecordNotFoundException(HttpServletRequest request, Exception ex){
        return new ResponseEntity<>(new FieldError(ex.getMessage(),ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
