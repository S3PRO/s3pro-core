package com.s3procore.dto.user.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileBeanDto {

    private String firstName;

    private String lastName;

    private String nickName;

    private String displayName;

    private String preferredLanguage;

}
