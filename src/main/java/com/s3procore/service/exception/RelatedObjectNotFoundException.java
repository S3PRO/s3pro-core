package com.s3procore.service.exception;

public class RelatedObjectNotFoundException extends ObjectNotFoundException {

    public RelatedObjectNotFoundException(String message) {
        super(message);
    }

    public RelatedObjectNotFoundException(Object id, Class<?> objectClass) {
        super(id, objectClass);
    }
}
