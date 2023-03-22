package com.s3procore.dto.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBeanDto {

    private String userName;

    private UserProfileBeanDto profile;

    private UserEmailBeanDto email;

    private UserPhoneBeanDto phone;

    private String password;

    private boolean passwordChangeRequired;

    private boolean requestPasswordlessRegistration;
}
