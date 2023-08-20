package com.example.spring.batch.error;

import com.example.core.model.RecordNotFoundException;
import com.example.core.model.ResultData;
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
    public ResponseEntity<ResultData> handleRecordNotFoundException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(ResultData.builder().success(false).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultData> handleException(HttpServletRequest request, Exception ex) {
        logger.error("", ex);
        return new ResponseEntity<>(ResultData.builder().success(false).message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
