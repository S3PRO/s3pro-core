package com.s3procore.service.user.converters;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.model.User;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CreateUpdateUserDtoToEntityConverter extends AbstractConverter<CreateUpdateUserDto, User> {

    @Override
    protected User generateTarget() {
        return new User();
    }

    @Override
    public void convert(CreateUpdateUserDto source, User target) {
        target.setUserName(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setLang(new Locale("en"));
    }
}
