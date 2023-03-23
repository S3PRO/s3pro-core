package com.s3procore.service.user.client;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.CreateUpdateUserMachineDto;
import com.s3procore.dto.user.UserMachineTokenDto;
import com.s3procore.dto.user.bean.user.UserBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.SecretUserMachineBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.UserMachineBeanResponseDto;

public interface UserClient {

    UserBeanResponseDto createUser(CreateUpdateUserDto createUpdateUserDto);

    UserMachineBeanResponseDto createUserMachine(CreateUpdateUserMachineDto createUpdateUserMachineDto);

    SecretUserMachineBeanResponseDto createSecretUserMachine(String userId);

    UserMachineTokenDto getUserMachineToken(String clientId, String clientSecret);

}
