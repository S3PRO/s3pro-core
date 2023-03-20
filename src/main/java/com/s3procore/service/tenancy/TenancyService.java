package com.s3procore.service.tenancy;

import com.s3procore.dto.TenancyDto;

public interface TenancyService {

    TenancyDto findById(Long id);

    TenancyDto createTenancy(TenancyDto tenancyDto);

    TenancyDto updateTenancy(Long id, TenancyDto tenancyDto);

}
