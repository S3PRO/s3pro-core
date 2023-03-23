package com.s3procore.service.user.converters;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.bean.user.UserBeanDto;
import com.s3procore.dto.user.bean.user.UserEmailBeanDto;
import com.s3procore.dto.user.bean.user.UserPhoneBeanDto;
import com.s3procore.dto.user.bean.user.UserProfileBeanDto;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CreateUpdateUserDtoToUserBeanDtoConverter extends AbstractConverter<CreateUpdateUserDto, UserBeanDto> {

    @Override
    protected UserBeanDto generateTarget() {
        return new UserBeanDto();
    }

    @Override
    public void convert(CreateUpdateUserDto source, UserBeanDto target) {
        target.setUserName(source.getEmail());
        target.setProfile(UserProfileBeanDto.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .nickName(source.getEmail())
                .displayName(String.join(" ", source.getFirstName(), source.getLastName()))
                .preferredLanguage(new Locale("en").getLanguage())
                .build());
        target.setEmail(UserEmailBeanDto.builder()
                .email(source.getEmail())
                .isEmailVerified(true)
                .build());
        target.setPhone(UserPhoneBeanDto.builder()
                .phone(source.getPhoneNumber())
                .isPhoneVerified(true)
                .build());
        target.setPassword(source.getPassword());
        target.setPasswordChangeRequired(false);
        target.setRequestPasswordlessRegistration(false);
    }
}
