package com.s3procore.service.user.converters;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.user.UserDto;
import com.s3procore.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToDtoConverter extends AbstractConverter<User, UserDto> {

    @Override
    protected UserDto generateTarget() {
        return new UserDto();
    }

    @Override
    public void convert(User source, UserDto target) {
        target.setId(source.getId());
        target.setUserName(source.getUserName());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setLang(source.getLang());
    }
}
