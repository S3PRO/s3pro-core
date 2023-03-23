package com.s3procore.web;

import com.s3procore.core.security.AuthenticationHelper;
import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.UserDto;
import com.s3procore.model.user.UserDetails;
import com.s3procore.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.s3procore.core.util.ControllerUtils.getErrorMessages;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUpdateUserDto newUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(getErrorMessages(bindingResult), HttpStatus.NOT_ACCEPTABLE);
        }

        final UserDto user = userService.create(newUser);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public UserDetails getUser() {
        UserDetails userDetails = authenticationHelper.getAuthenticationDetails();

        return userDetails;
    }

}
