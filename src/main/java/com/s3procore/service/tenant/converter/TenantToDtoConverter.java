package com.s3procore.service.tenant.converter;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.tenant.TenantDto;
import com.s3procore.model.tenant.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantToDtoConverter extends AbstractConverter<Tenant, TenantDto> {

    @Override
    protected TenantDto generateTarget() {
        return new TenantDto();
    }

    @Override
    public void convert(Tenant source, TenantDto target) {
        target.setId(source.getId());
        target.setDomainName(source.getDomainName());
        target.setLocation(source.getLocation());
        target.setEnvironment(source.getEnvironment());
    }
}
