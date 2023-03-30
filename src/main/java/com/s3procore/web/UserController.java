package com.s3procore.web;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.UserDto;
import com.s3procore.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @SecurityRequirements
    public UserDto create(@RequestBody CreateUpdateUserDto userDto) {
        return userService.create(userDto);
    }

}
