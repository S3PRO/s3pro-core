package com.s3procore.dto.user.bean.usermachine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.s3procore.dto.user.ApplicationTokenDto;
import com.s3procore.dto.user.bean.user.UserBeanDetailsResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationBeanResponseDto {

    private String userId;

    private String clientId;

    private String clientSecret;

    private UserBeanDetailsResponseDto details;

    private ApplicationTokenDto token;

}
