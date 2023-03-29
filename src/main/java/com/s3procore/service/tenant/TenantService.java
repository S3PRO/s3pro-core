package com.s3procore.service.tenant;

import com.s3procore.dto.tenant.AvailabilityDomainDto;
import com.s3procore.dto.tenant.DomainDto;
import com.s3procore.dto.tenant.TenantDto;
import com.s3procore.model.tenant.LocationTenant;

import java.util.List;

public interface TenantService {

    TenantDto create(TenantDto tenantDto);

    DomainDto generateDomain(LocationTenant location);

    AvailabilityDomainDto isAvailabilityDomain(String domainName, LocationTenant location);

    TenantDto update(TenantDto tenantDto);

    List<TenantDto> list();

    TenantDto getDetails(Long id);

    void delete(Long id);
}
