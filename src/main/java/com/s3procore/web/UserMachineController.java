package com.s3procore.web;

import com.s3procore.dto.user.CreateUpdateUserMachineDto;
import com.s3procore.dto.user.GenerateUserMachineTokenDto;
import com.s3procore.dto.user.UserMachineTokenDto;
import com.s3procore.dto.user.bean.usermachine.UserMachineBeanResponseDto;
import com.s3procore.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-machines")
@AllArgsConstructor
public class UserMachineController {

    private final UserService userService;

    @PostMapping
    public UserMachineBeanResponseDto create(@RequestBody CreateUpdateUserMachineDto userMachineDto) {
        return userService.createUserMachine(userMachineDto);
    }

    @PostMapping("/generate-token")
    public UserMachineTokenDto generateToken(@RequestBody GenerateUserMachineTokenDto generateUserMachineTokenDto) {
        return userService.generateUserMachineToken(generateUserMachineTokenDto);
    }

}
