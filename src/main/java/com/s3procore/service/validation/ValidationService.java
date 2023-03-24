package com.s3procore.service.validation;

import com.s3procore.service.exception.ValidationException;

/**
 * Validation service
 */
public interface ValidationService {

    /**
     * Validate field method.
     * @param target the object that is to be validated
     * @param validationHints validation groups
     *
     * @throws ValidationException
     */
    void validate(Object target, Object... validationHints) throws ValidationException;
}
