package com.s3procore.dto;

import com.s3procore.service.validation.ValidationCodes;
import jakarta.validation.constraints.NotNull;
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
public class TenancyDto {

    private Long id;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String accountType;

    @NotNull(message = ValidationCodes.REQUIRED)
    private String companyName;


}
