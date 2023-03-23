package com.s3procore.service.user.converters;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.user.CreateUpdateUserMachineDto;
import com.s3procore.dto.user.bean.usermachine.UserBeanMachineDto;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdateUserMachineDtoToUserMachineBeanDtoConverter extends AbstractConverter<CreateUpdateUserMachineDto, UserBeanMachineDto> {

    private final String ACCESS_TOKEN_TYPE = "ACCESS_TOKEN_TYPE_JWT";

    @Override
    protected UserBeanMachineDto generateTarget() {
        return new UserBeanMachineDto();
    }

    @Override
    public void convert(CreateUpdateUserMachineDto source, UserBeanMachineDto target) {
        target.setUserName(source.getCompanyName());
        target.setName(source.getCompanyName());
        target.setDescription(source.getCompanyName());
        target.setAccessTokenType(ACCESS_TOKEN_TYPE);
    }
}
