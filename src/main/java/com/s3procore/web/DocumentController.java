package com.s3procore.web;

import com.s3procore.dto.document.DocumentDto;
import com.s3procore.service.document.DocumentService;
import com.s3procore.core.subdomain.annotation.TenantController;
import com.s3procore.core.subdomain.context.TenantContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@AllArgsConstructor
@TenantController
public class DocumentController {

    private final DocumentService documentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto create(@RequestBody DocumentDto documentDto) {
        String domainName = TenantContext.getTenant().getDomainName();
        return documentService.create(domainName, documentDto);
    }

}
