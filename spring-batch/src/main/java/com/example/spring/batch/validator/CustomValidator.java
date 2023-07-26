package com.example.spring.batch.validator;

import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class CustomValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    protected String getMessageException(Set<ConstraintViolation<Object>> constraintViolations) {
        StringBuilder messageException = new StringBuilder();
        for (ConstraintViolation<Object> violation : constraintViolations) {
            messageException.append(violation.getPropertyPath());
            messageException.append(" have error: ");
            messageException.append(violation.getMessage());
            messageException.append(" | ");
        }
        return messageException.toString();
    }
}
