package com.s3procore.dto.user.bean.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBeanResponseDto {

    private String userId;

    private UserBeanDetailsResponseDto details;

}
