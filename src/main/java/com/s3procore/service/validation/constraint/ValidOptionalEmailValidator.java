package com.s3procore.service.validation.constraint;

import org.apache.commons.validator.routines.EmailValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class ValidOptionalEmailValidator implements ConstraintValidator<OptionalEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (Optional.ofNullable(email).isPresent()) {
            EmailValidator instance = EmailValidator.getInstance();
            return instance.isValid(email);
        }
        return false;
    }
}
