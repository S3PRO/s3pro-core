package com.s3procore.service.document;

import com.s3procore.core.security.AuthenticationHelper;
import com.s3procore.dto.document.DocumentDto;
import com.s3procore.model.Company;
import com.s3procore.model.Document;
import com.s3procore.model.user.UserDetails;
import com.s3procore.repository.CompanyRepository;
import com.s3procore.repository.DocumentRepository;
import com.s3procore.service.document.converter.DocumentToDtoConverter;
import com.s3procore.service.exception.RelatedObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final CompanyRepository companyRepository;
    private final AuthenticationHelper authenticationHelper;
    private final DocumentToDtoConverter documentToDtoConverter;

    @Override
    @Transactional
    public DocumentDto create(DocumentDto documentDto) {
        UserDetails userDetails = authenticationHelper.getAuthenticationDetails();

        Company company = companyRepository.findById(userDetails.getCompanyId())
                .orElseThrow(() -> new RelatedObjectNotFoundException(userDetails.getCompanyId(), Company.class));

        documentDto.setId(null);

        Document document = new Document();
        document.setName(documentDto.getName());
        document.setCompany(company);

        document = documentRepository.save(document);

        return documentToDtoConverter.convert(document);
    }
}
