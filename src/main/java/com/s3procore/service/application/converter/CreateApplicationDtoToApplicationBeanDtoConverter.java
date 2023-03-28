package com.s3procore.service.application.converter;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.application.CreateApplicationDto;
import com.s3procore.dto.user.bean.usermachine.ApplicationBeanDto;
import org.springframework.stereotype.Component;

@Component
public class CreateApplicationDtoToApplicationBeanDtoConverter extends AbstractConverter<CreateApplicationDto, ApplicationBeanDto> {

    private final String ACCESS_TOKEN_TYPE = "ACCESS_TOKEN_TYPE_JWT";

    @Override
    protected ApplicationBeanDto generateTarget() {
        return new ApplicationBeanDto();
    }

    @Override
    public void convert(CreateApplicationDto source, ApplicationBeanDto target) {
        target.setUserName(source.getApplicationName());
        target.setName(source.getApplicationName());
        target.setDescription(source.getApplicationName());
        target.setAccessTokenType(ACCESS_TOKEN_TYPE);
    }
}
