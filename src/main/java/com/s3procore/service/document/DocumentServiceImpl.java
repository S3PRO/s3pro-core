package com.s3procore.service.document;

import com.s3procore.core.security.AuthenticationHelper;
import com.s3procore.dto.document.DocumentDto;
import com.s3procore.dto.security.AuthenticationDetailsDto;
import com.s3procore.model.Document;
import com.s3procore.model.tenant.Tenant;
import com.s3procore.repository.DocumentRepository;
import com.s3procore.repository.TenantRepository;
import com.s3procore.service.document.converter.DocumentToDtoConverter;
import com.s3procore.service.exception.RelatedObjectNotFoundException;
import com.s3procore.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final TenantRepository tenantRepository;
    private final AuthenticationHelper authenticationHelper;
    private final DocumentToDtoConverter documentToDtoConverter;
    private final ValidationService validationService;

    @Override
    @Transactional
    public DocumentDto create(String domainName, DocumentDto documentDto) {
        validationService.validate(documentDto);

        AuthenticationDetailsDto authenticationDetails = authenticationHelper.getAuthenticationDetailsByDomainName(domainName);

        Tenant tenant = tenantRepository.findByDomainName(domainName)
                .orElseThrow(() -> new RelatedObjectNotFoundException(domainName, Tenant.class));

        documentDto.setId(null);

        Document document = new Document();
        document.setName(documentDto.getName());
        document.setTenant(tenant);

        document = documentRepository.save(document);

        return documentToDtoConverter.convert(document);
    }
}
