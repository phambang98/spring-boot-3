package com.example.springmq.validation;

import com.example.springmq.model.AbstractBaseRequest;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AbstractBaseValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AbstractBaseRequest baseBean = (AbstractBaseRequest) target;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = factory.getValidator();
        validator.validate(baseBean);
    }
}
