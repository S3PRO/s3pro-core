package com.s3procore.dto.application;

import com.s3procore.model.application.ApplicationType;
import com.s3procore.service.validation.ValidationCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationDto {

    @NotNull(message = ValidationCode.REQUIRED)
    private ApplicationType applicationType;

    @NotNull(message = ValidationCode.REQUIRED)
    private String applicationName;

}
