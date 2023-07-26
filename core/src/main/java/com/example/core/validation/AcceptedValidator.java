package com.example.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class AcceptedValidator implements ConstraintValidator<AcceptedValues, Object> {

    private List<String> valueList;

    @Override
    public void initialize(AcceptedValues constraintAnnotation) {
        valueList = new ArrayList<>();
        for (Object val : constraintAnnotation.acceptValues()) {
            String tmp = String.valueOf(val);
            valueList.add(tmp.toLowerCase());
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String tmp = String.valueOf(value);
        return valueList.contains(tmp.toLowerCase());
    }

}