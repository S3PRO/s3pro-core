package com.s3procore.service.tenant;

import com.s3procore.core.security.AuthenticationHelper;
import com.s3procore.dto.tenant.AvailabilityDomainDto;
import com.s3procore.dto.tenant.DomainDto;
import com.s3procore.dto.tenant.TenantDto;
import com.s3procore.model.tenant.LocationTenant;
import com.s3procore.model.tenant.Tenant;
import com.s3procore.model.user.User;
import com.s3procore.repository.TenantRepository;
import com.s3procore.repository.UserRepository;
import com.s3procore.service.exception.ObjectNotFoundException;
import com.s3procore.service.exception.RelatedObjectNotFoundException;
import com.s3procore.service.tenant.converter.TenantToDtoConverter;
import com.s3procore.service.tenant.util.TenantDomainShortUuidGeneratorUtil;
import com.s3procore.service.tenant.validator.TenantValidator;
import com.s3procore.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final AuthenticationHelper authenticationHelper;
    private final ValidationService validationService;
    private final TenantToDtoConverter tenantToDtoConverter;
    private final TenantValidator tenantValidator;

    @Value("${tenant.url.pattern}")
    private String tenantUrlPattern;

    @Override
    @Transactional
    public TenantDto create(TenantDto tenantDto) {
        validationService.validate(tenantDto);

        tenantValidator.validate(tenantDto);

        Long userId = authenticationHelper.getAuthenticationDetails().getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RelatedObjectNotFoundException(userId, User.class));

        Tenant tenant = new Tenant();
        tenant.setDomainName(tenantDto.getDomainName());
        tenant.setLocation(tenantDto.getLocation());
        tenant.setEnvironment(tenantDto.getEnvironment());
        tenant.addUser(user);

        return tenantToDtoConverter.convert(tenantRepository.save(tenant));
    }

    @Override
    public DomainDto generateDomain(LocationTenant location) {
        String domainName = tenantUrlPattern
                .replace("{location}", location.name().toLowerCase())
                .replace("{shortUuid}", TenantDomainShortUuidGeneratorUtil.generateShortUuid());

        return DomainDto.builder()
                .domainName(domainName)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AvailabilityDomainDto isAvailabilityDomain(String domainName, LocationTenant location) {
        boolean isExists = tenantRepository.existsByDomainNameAndLocation(domainName.toLowerCase(), location);

        return AvailabilityDomainDto.builder()
                .available(!isExists)
                .build();
    }

    @Override
    @Transactional
    public TenantDto update(TenantDto tenantDto) {
        validationService.validate(tenantDto);

        tenantValidator.validate(tenantDto);

        Tenant tenant = tenantRepository.findById(tenantDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException(tenantDto.getId(), Tenant.class));

        tenant.setDomainName(tenantDto.getDomainName());
        tenant.setEnvironment(tenantDto.getEnvironment());
        tenant.setLocation(tenantDto.getLocation());

        return tenantToDtoConverter.convert(tenantRepository.save(tenant));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantDto> list() {
        Long userId = authenticationHelper.getAuthenticationDetails().getUserId();

        return tenantRepository.findAllByUserId(userId).stream()
                .map(tenantToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TenantDto getDetails(Long id) {
        return tenantRepository.findById(id).map(tenantToDtoConverter::convert)
                .orElseThrow(() -> new ObjectNotFoundException(id, Tenant.class));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Tenant.class));

       for (User user : tenant.getUsers()) {
           tenant.removeUser(user);
       }

        tenantRepository.delete(tenant);
    }

}
