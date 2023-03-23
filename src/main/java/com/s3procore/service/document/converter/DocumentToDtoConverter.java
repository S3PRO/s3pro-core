package com.s3procore.service.document.converter;

import com.s3procore.core.converter.AbstractConverter;
import com.s3procore.dto.document.DocumentDto;
import com.s3procore.model.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentToDtoConverter extends AbstractConverter<Document, DocumentDto> {

    @Override
    protected DocumentDto generateTarget() {
        return new DocumentDto();
    }

    @Override
    public void convert(Document source, DocumentDto target) {
        target.setId(source.getId());
        target.setName(source.getName());
    }
}
