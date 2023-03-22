package com.s3procore.service.user.client;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.bean.UserBeanResponseDto;

public interface UserClient {

    UserBeanResponseDto createUser(CreateUpdateUserDto createUpdateUserDto);

}
