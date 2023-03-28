package com.s3procore.service.user.converter;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdateUserDtoToEntityConverter extends AbstractConverter<CreateUpdateUserDto, User> {

    @Override
    protected User generateTarget() {
        return new User();
    }

    @Override
    public void convert(CreateUpdateUserDto source, User target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
    }
}
