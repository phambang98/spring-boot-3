package com.example.springcore.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AcceptedValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptedValues {

    String[] acceptValues();

    String message() default "Not allowded value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
