package com.s3procore.service.user.client;

import com.s3procore.dto.application.CreateApplicationDto;
import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.ApplicationTokenDto;
import com.s3procore.dto.user.bean.user.UserBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.SecretApplicationBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.ApplicationBeanResponseDto;

public interface UserClient {

    UserBeanResponseDto createUser(CreateUpdateUserDto createUpdateUserDto);

    ApplicationBeanResponseDto createApplication(CreateApplicationDto createApplicationDto);

    SecretApplicationBeanResponseDto createApplicationSecretCredentials(String userId);

    ApplicationTokenDto getApplicationToken(String clientId, String clientSecret);

}
