package com.s3procore.web;

import com.s3procore.dto.application.ApplicationDto;
import com.s3procore.dto.application.CreateApplicationDto;
import com.s3procore.dto.user.GenerateUserMachineTokenDto;
import com.s3procore.dto.user.ApplicationTokenDto;
import com.s3procore.service.application.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/tenant/{tenantId}/create")
    public ApplicationDto create(@PathVariable Long tenantId, @RequestBody CreateApplicationDto createApplicationDto) {
        return applicationService.create(tenantId, createApplicationDto);
    }

    @PostMapping("/generate-token")
    public ApplicationTokenDto generateToken(@RequestBody GenerateUserMachineTokenDto generateUserMachineTokenDto) {
        return applicationService.generateUserMachineToken(generateUserMachineTokenDto);
    }

}
