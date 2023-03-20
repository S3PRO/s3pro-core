package com.s3procore.service.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Validation exception. It extends {@link RuntimeException} in order to revert transaction in case of validation error.
 */
public class ValidationException extends RuntimeException {

    @Getter
    private List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = List.copyOf(errors);
    }

    public ValidationException(Error error) {
        this(List.of(error));
    }

    public ValidationException(String errorCode) {
        Error error = Error.builder()
                .code(errorCode)
                .build();
        this.errors = List.of(error);
    }

    @Override
    public String getMessage() {
        return errors.stream()
                        .map(Error::getCode)
                        .collect(Collectors.joining(", "));
    }

    @Getter
    @Builder
    public static class Error {

        /**
         * Field name. Can be null in case of general error (not related to any field).
         */
        private String field;

        /**
         * Error code
         */
        private String code;
    }
}
