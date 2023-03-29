package com.s3procore.web;

import com.s3procore.dto.tenant.AvailabilityDomainDto;
import com.s3procore.dto.tenant.DomainDto;
import com.s3procore.dto.tenant.TenantDto;
import com.s3procore.model.tenant.LocationTenant;
import com.s3procore.service.tenant.TenantService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@AllArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TenantDto create(@RequestBody TenantDto tenantDto) {
        return tenantService.create(tenantDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TenantDto update(@PathVariable Long id, @RequestBody TenantDto tenantDto) {
        tenantDto.setId(id);
        return tenantService.update(tenantDto);
    }

    @GetMapping("/generate-domain")
    public DomainDto generateDomain(@RequestParam LocationTenant location) {
        return tenantService.generateDomain(location);
    }

    @GetMapping("/availability-domain")
    public AvailabilityDomainDto availabilityDomain(@RequestParam String domainName, @RequestParam LocationTenant location) {
        return tenantService.isAvailabilityDomain(domainName, location);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TenantDto> list() {
        return tenantService.list();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TenantDto getDetails(@PathVariable Long id) {
        return tenantService.getDetails(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        tenantService.delete(id);
    }
}
