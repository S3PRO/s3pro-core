package com.s3procore.service.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationCode {
    public static final String INVALID_LENGTH = "INVALID_LENGTH";
    public static final String ZITADEL_API_ERROR = "ZITADEL_API_ERROR";
    public static final String REQUIRED = "REQUIRED";
    public static final String INVALID_PHONE_NUMBER_FORMAT = "INVALID_PHONE_NUMBER_FORMAT";
    public static final String INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
    public static final String INVALID_ATTRIBUTES = "INVALID_ATTRIBUTES";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String DOMAIN_NAME_EXISTS = "DOMAIN_NAME_ALREADY_EXISTS";

}
