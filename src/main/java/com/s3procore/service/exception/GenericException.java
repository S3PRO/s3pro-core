package com.s3procore.service.exception;

import lombok.Getter;

public class GenericException extends RuntimeException {

    @Getter
    private final String errorCode;

    @Getter
    private String details;

    public GenericException(String errorCode, Exception ex) {
        super(ex);
        this.errorCode = errorCode;
    }

    public GenericException(String errorCode, String details) {
        this.errorCode = errorCode;
        this.details = details;
    }

    public GenericException(String errorCode, String details, Exception ex) {
        super(ex);
        this.errorCode = errorCode;
        this.details = details;
    }

}
