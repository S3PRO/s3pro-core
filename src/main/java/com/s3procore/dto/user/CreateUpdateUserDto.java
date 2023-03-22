package com.s3procore.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateUserDto {

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String password;

}
