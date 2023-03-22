package com.s3procore.web;


import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.UserDto;
import com.s3procore.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.s3procore.core.util.ControllerUtils.getErrorMessages;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/api/user")
    public ResponseEntity<?> create(@RequestBody @Validated CreateUpdateUserDto newUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(getErrorMessages(bindingResult), HttpStatus.NOT_ACCEPTABLE);
        }

        final UserDto user = userService.create(newUser);
        return ResponseEntity.ok(user);
    }

}
