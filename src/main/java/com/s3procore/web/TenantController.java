package com.s3procore.web;

import com.s3procore.dto.tenant.AvailabilityDomainDto;
import com.s3procore.dto.tenant.DomainDto;
import com.s3procore.dto.tenant.TenantDto;
import com.s3procore.model.tenant.LocationTenant;
import com.s3procore.service.tenant.TenantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
@AllArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public TenantDto create(@RequestBody TenantDto tenantDto) {
        return tenantService.create(tenantDto);
    }

    @GetMapping("/generate-domain")
    public DomainDto generateDomain(@RequestParam LocationTenant location) {
        return tenantService.generateDomain(location);
    }

    @GetMapping("/availability-domain")
    public AvailabilityDomainDto availabilityDomain(@RequestParam String domainName, @RequestParam LocationTenant location) {
        return tenantService.isAvailabilityDomain(domainName, location);
    }

}
