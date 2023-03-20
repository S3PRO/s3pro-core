package com.s3procore.service.tenancy.converters;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.TenancyDto;
import com.s3procore.model.Tenancy;
import org.springframework.stereotype.Component;

@Component
public class TenancyToDtoConverter extends AbstractConverter<Tenancy, TenancyDto> {

    @Override
    protected TenancyDto generateTarget() {
        return new TenancyDto();
    }

    @Override
    public void convert(Tenancy source, TenancyDto target) {
        target.setId(source.getId());
        target.setAccountType(source.getAccountType());
        target.setCompanyName(source.getCompanyName());
    }
}
