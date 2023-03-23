package com.s3procore.service.user.converters;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.user.UserMachineDto;
import com.s3procore.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserMachineDtoConverter extends AbstractConverter<User, UserMachineDto> {

    @Override
    protected UserMachineDto generateTarget() {
        return new UserMachineDto();
    }

    @Override
    public void convert(User source, UserMachineDto target) {
        target.setId(source.getId());
        target.setCompanyId(source.getCompany().getId());
        target.setCompanyName(source.getCompany().getName());
    }
}
