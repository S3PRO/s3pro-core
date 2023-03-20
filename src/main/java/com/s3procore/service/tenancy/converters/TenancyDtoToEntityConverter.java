package com.s3procore.service.tenancy.converters;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.TenancyDto;
import com.s3procore.model.Tenancy;
import org.springframework.stereotype.Component;

@Component
public class TenancyDtoToEntityConverter extends AbstractConverter<TenancyDto, Tenancy> {

    @Override
    protected Tenancy generateTarget() {
        return new Tenancy();
    }

    @Override
    public void convert(TenancyDto source, Tenancy target) {
        target.setId(source.getId());
        target.setAccountType(source.getAccountType());
        target.setCompanyName(source.getCompanyName());
    }
}
