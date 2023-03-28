package com.s3procore.dto.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationDetailsDto {

    private String login;

    private Long tenantId;

    private Long userId;

}
