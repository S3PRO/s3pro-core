package com.s3procore.dto.user;

import com.s3procore.service.validation.ValidationCode;
import com.s3procore.service.validation.constraint.OptionalEmail;
import com.s3procore.service.validation.constraint.ValidPhoneNumber;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateUserDto {

    @NotNull(message = ValidationCode.REQUIRED)
    @OptionalEmail(message = ValidationCode.INVALID_EMAIL_FORMAT)
    private String email;

    @NotNull(message = ValidationCode.REQUIRED)
    @ValidPhoneNumber
    private String phoneNumber;

    @NotNull(message = ValidationCode.REQUIRED)
    private String firstName;

    @NotNull(message = ValidationCode.REQUIRED)
    private String lastName;

    @NotNull(message = ValidationCode.REQUIRED)
    private String password;

}
