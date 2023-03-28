package com.s3procore.service.application;

import com.s3procore.core.security.AuthenticationHelper;
import com.s3procore.dto.application.ApplicationDto;
import com.s3procore.dto.application.CreateApplicationDto;
import com.s3procore.dto.user.ApplicationTokenDto;
import com.s3procore.dto.user.GenerateUserMachineTokenDto;
import com.s3procore.dto.user.bean.usermachine.ApplicationBeanResponseDto;
import com.s3procore.model.application.Application;
import com.s3procore.model.application.ApplicationGrantType;
import com.s3procore.model.tenant.Tenant;
import com.s3procore.repository.ApplicationRepository;
import com.s3procore.repository.TenantRepository;
import com.s3procore.service.application.converter.EntityToApplicationDtoConverter;
import com.s3procore.service.exception.ObjectNotFoundException;
import com.s3procore.service.user.client.UserClient;
import com.s3procore.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final TenantRepository tenantRepository;
    private final AuthenticationHelper authenticationHelper;
    private final UserClient userClient;
    private final ValidationService validationService;
    private final EntityToApplicationDtoConverter entityToApplicationDtoConverter;

    @Override
    @Transactional
    public ApplicationDto create(Long tenantId, CreateApplicationDto createApplicationDto) {
        validationService.validate(createApplicationDto);

        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new ObjectNotFoundException(tenantId, Tenant.class));

        ApplicationBeanResponseDto applicationBeanResponseDto = userClient.createApplication(createApplicationDto);

        Application application = new Application();
        application.setApplicationType(createApplicationDto.getApplicationType());
        application.setApplicationName(createApplicationDto.getApplicationName());
        application.setSubId(applicationBeanResponseDto.getUserId());
        application.setClientId(applicationBeanResponseDto.getClientId());
        application.setClientSecret(applicationBeanResponseDto.getClientSecret());
        application.setApplicationGrantType(ApplicationGrantType.CLIENT_CREDENTIALS);
        application.setTenant(tenant);

        return entityToApplicationDtoConverter.convert(applicationRepository.save(application));
    }

    @Override
    @Transactional
    public ApplicationTokenDto generateUserMachineToken(GenerateUserMachineTokenDto generateUserMachineTokenDto) {
        return userClient.getApplicationToken(generateUserMachineTokenDto.getClientId(), generateUserMachineTokenDto.getClientSecret());
    }
}
