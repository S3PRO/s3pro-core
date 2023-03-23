package com.s3procore.dto.user;

import com.s3procore.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateUserMachineDto {

    private Long id;

    private UserType type;

    private String companyName;

    private String companySize;

    private String clientId;

    private String secretCode;

}
