package com.s3procore.service.validation.constraint;

import com.s3procore.service.validation.ValidationCode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(regexp = "^\\+(?:[0-9] ?){11}[0-9]$", message = ValidationCode.INVALID_PHONE_NUMBER_FORMAT)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidPhoneNumber {

    String message() default ValidationCode.INVALID_PHONE_NUMBER_FORMAT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}