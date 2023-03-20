package com.s3procore.web;

import com.s3procore.dto.TenancyDto;
import com.s3procore.service.tenancy.TenancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenancy")
public class TenancyController {

    private final TenancyService tenancyService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TenancyDto getTenancy(@PathVariable Long id) {
        return tenancyService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TenancyDto createTenancy(@RequestBody TenancyDto tenancyDto) {
        return tenancyService.createTenancy(tenancyDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TenancyDto updateTenancy(@PathVariable Long id, @RequestBody TenancyDto tenancyDto) {
        tenancyDto.setId(id);
        return tenancyService.updateTenancy(id, tenancyDto);
    }

}
