package com.s3procore.dto.tenant;

import com.s3procore.model.tenant.EnvironmentTenant;
import com.s3procore.model.tenant.LocationTenant;
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
public class TenantDto {

    private Long id;

    @NotNull(message = ValidationCode.REQUIRED)
    private String domainName;

    @NotNull(message = ValidationCode.REQUIRED)
    private LocationTenant location;

    @NotNull(message = ValidationCode.REQUIRED)
    private EnvironmentTenant environment;

}
