package com.s3procore.service.tenant.validator;

import com.s3procore.dto.tenant.TenantDto;
import com.s3procore.repository.TenantRepository;
import com.s3procore.service.exception.ValidationException;
import com.s3procore.service.validation.ValidationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantValidatorImpl implements TenantValidator {

    private final TenantRepository tenantRepository;

    @Override
    public void validate(TenantDto tenantDto) {
        boolean isExistDomainName;

        if (tenantDto.getId() == null) {
            isExistDomainName = tenantRepository.existsByDomainNameAndLocation(tenantDto.getDomainName(), tenantDto.getLocation());
        } else {
            isExistDomainName = tenantRepository
                    .existsByDomainNameAndLocationAndIdNot(tenantDto.getDomainName(), tenantDto.getLocation(), tenantDto.getId());
        }

        if (isExistDomainName) {
            throw new ValidationException(ValidationCode.DOMAIN_NAME_EXISTS);
        }
    }
}
