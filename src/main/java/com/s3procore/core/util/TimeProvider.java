package com.s3procore.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class TimeProvider {

    public LocalDateTime now() {
        return ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();
    }
}
