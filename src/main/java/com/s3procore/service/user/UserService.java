package com.s3procore.service.user;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.UserDto;

public interface UserService {

    UserDto create(CreateUpdateUserDto userDto);

}
