package com.s3procore.dto.user;

import com.s3procore.service.validation.ValidationCodes;
import com.s3procore.service.validation.constraints.OptionalEmail;
import com.s3procore.service.validation.constraints.ValidPhoneNumber;
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

    @NotNull(message = ValidationCodes.REQUIRED)
    @OptionalEmail(message = ValidationCodes.INVALID_EMAIL_FORMAT)
    private String email;

    @NotNull(message = ValidationCodes.REQUIRED)
    @ValidPhoneNumber
    private String phoneNumber;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String firstName;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String lastName;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String password;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String companyName;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String companySize;

}
