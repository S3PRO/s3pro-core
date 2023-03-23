package com.s3procore.service.user;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.CreateUpdateUserMachineDto;
import com.s3procore.dto.user.GenerateUserMachineTokenDto;
import com.s3procore.dto.user.UserDto;
import com.s3procore.dto.user.UserMachineTokenDto;
import com.s3procore.dto.user.bean.usermachine.UserMachineBeanResponseDto;

public interface UserService {

    UserDto create(CreateUpdateUserDto userDto);

    UserMachineBeanResponseDto createUserMachine(CreateUpdateUserMachineDto userMachineDto);

    UserMachineTokenDto generateUserMachineToken(GenerateUserMachineTokenDto generateUserMachineTokenDto);

}
