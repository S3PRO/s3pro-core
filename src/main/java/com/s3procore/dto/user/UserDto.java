package com.s3procore.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s3procore.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private UserType type;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Locale lang;

    private Long companyId;

    private String companyName;

}
