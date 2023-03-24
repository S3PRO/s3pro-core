package com.s3procore.service.validation.constraints;

import com.s3procore.service.validation.ValidationCodes;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidOptionalEmailValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalEmail {
    String message() default ValidationCodes.INVALID_ATTRIBUTES;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
