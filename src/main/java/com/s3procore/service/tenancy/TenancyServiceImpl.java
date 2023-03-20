package com.s3procore.service.tenancy;

import com.s3procore.dto.TenancyDto;
import com.s3procore.model.Tenancy;
import com.s3procore.repository.TenancyRepository;
import com.s3procore.service.exception.ObjectNotFoundException;
import com.s3procore.service.tenancy.converters.TenancyDtoToEntityConverter;
import com.s3procore.service.tenancy.converters.TenancyToDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenancyServiceImpl implements TenancyService {

    private final TenancyRepository tenancyRepository;
    private final TenancyToDtoConverter tenancyToDtoConverter;
    private final TenancyDtoToEntityConverter tenancyDtoToEntityConverter;

    @Override
    @Transactional(readOnly = true)
    public TenancyDto findById(Long id) {
        return tenancyRepository.findById(id)
                .map(tenancyToDtoConverter::convert)
                .orElseThrow(() -> new ObjectNotFoundException(id, Tenancy.class));
    }

    @Override
    @Transactional
    public TenancyDto createTenancy(TenancyDto tenancyDto) {
        return tenancyToDtoConverter.convert(tenancyRepository.save(tenancyDtoToEntityConverter.convert(tenancyDto)));
    }

    @Override
    @Transactional
    public TenancyDto updateTenancy(Long id, TenancyDto tenancyDto) {
        Tenancy tenancy = tenancyRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, Tenancy.class));

        tenancy.setAccountType(tenancyDto.getAccountType());
        tenancy.setCompanyName(tenancyDto.getCompanyName());

        Tenancy savedTenancy = tenancyRepository.save(tenancy);
        return tenancyToDtoConverter.convert(savedTenancy);
    }
}
