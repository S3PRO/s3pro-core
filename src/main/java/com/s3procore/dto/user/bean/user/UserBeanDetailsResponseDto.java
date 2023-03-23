package com.s3procore.dto.user.bean.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBeanDetailsResponseDto {

    private String sequence;

    private LocalDateTime creationDate;

    private String resourceOwner;

}
