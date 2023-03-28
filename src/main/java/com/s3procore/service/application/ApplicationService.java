package com.s3procore.service.application;

import com.s3procore.dto.application.ApplicationDto;
import com.s3procore.dto.application.CreateApplicationDto;
import com.s3procore.dto.user.ApplicationTokenDto;
import com.s3procore.dto.user.GenerateUserMachineTokenDto;

public interface ApplicationService {

    ApplicationDto create(Long tenantId, CreateApplicationDto createApplicationDto);

    ApplicationTokenDto generateUserMachineToken(GenerateUserMachineTokenDto generateUserMachineTokenDto);


}
