package com.example.springbatch.batch.validator;


import com.example.springcore.entity.Menu;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Set;

@Component
public class MenuValidator extends CustomValidator {

    @Override
    public void validate(Object target, Errors errors) {
        Menu bean = (Menu) target;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
//            bean.setValid(false);
//            bean.setErrorCode("E01");
//            bean.setErrorMessage(getMessageException(constraintViolations));
        }

    }
}
