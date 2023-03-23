package com.s3procore.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateUserMachineTokenDto {

    private String clientId;

    private String clientSecret;

}
