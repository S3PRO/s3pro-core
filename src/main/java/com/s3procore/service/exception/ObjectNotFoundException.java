package com.s3procore.service.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class ObjectNotFoundException extends RuntimeException {

    protected Object id;
    protected Class<?> objectClass;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(Object id, Class<?> objectClass) {
        this.id = id;
        this.objectClass = objectClass;
    }

    public boolean isMessagePresent() {
        return StringUtils.isNotEmpty(this.getMessage());
    }
}
