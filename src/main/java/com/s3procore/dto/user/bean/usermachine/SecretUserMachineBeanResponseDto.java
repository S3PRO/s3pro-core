package com.s3procore.dto.user.bean.usermachine;

import com.s3procore.dto.user.bean.user.UserBeanDetailsResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecretUserMachineBeanResponseDto {

    private String clientId;

    private String clientSecret;

    private UserBeanDetailsResponseDto details;

}
