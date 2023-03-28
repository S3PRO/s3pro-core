package com.s3procore.service.tenant;

import com.s3procore.dto.tenant.AvailabilityDomainDto;
import com.s3procore.dto.tenant.DomainDto;
import com.s3procore.dto.tenant.TenantDto;
import com.s3procore.model.tenant.LocationTenant;

public interface TenantService {

    TenantDto create(TenantDto tenantDto);

    DomainDto generateDomain(LocationTenant location);

    AvailabilityDomainDto isAvailabilityDomain(String domainName, LocationTenant location);

}
