package com.s3procore.dto.user;

import com.s3procore.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMachineDto {

    private Long id;

    private UserType type;

    private Long companyId;

    private String companyName;

    private String clientId;

    private String secretCode;

}
