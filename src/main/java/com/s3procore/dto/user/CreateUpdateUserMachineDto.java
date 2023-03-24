package com.s3procore.dto.user;

import com.s3procore.service.validation.ValidationCodes;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = ValidationCodes.REQUIRED)
    private String companyName;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String companySize;

    private String clientId;

    private String secretCode;

}
