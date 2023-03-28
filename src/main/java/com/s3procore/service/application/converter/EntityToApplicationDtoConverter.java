package com.s3procore.service.application.converter;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.application.ApplicationDto;
import com.s3procore.model.application.Application;
import org.springframework.stereotype.Component;

@Component
public class EntityToApplicationDtoConverter extends AbstractConverter<Application, ApplicationDto> {

    @Override
    protected ApplicationDto generateTarget() {
        return new ApplicationDto();
    }

    @Override
    public void convert(Application source, ApplicationDto target) {
        target.setId(source.getId());
        target.setApplicationType(source.getApplicationType());
        target.setApplicationName(source.getApplicationName());
        target.setClientId(source.getClientId());
        target.setClientSecret(source.getClientSecret());
        target.setApplicationGrantType(source.getApplicationGrantType());
    }
}
