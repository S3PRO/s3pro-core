package com.s3procore.service.document;

import com.s3procore.dto.document.DocumentDto;

public interface DocumentService {

    DocumentDto create(String domainName, DocumentDto documentDto);

}
