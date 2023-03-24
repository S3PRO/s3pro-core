package com.s3procore.service.validation;

import com.s3procore.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
class ValidationServiceImpl implements ValidationService {

    private final SmartValidator validator;

    @Override
    public void validate(Object target, Object... validationHints) throws ValidationException {
        MapBindingResult mapBindingResult = new MapBindingResult(new LinkedHashMap<String, String>(), "dto");
        validator.validate(target, mapBindingResult, validationHints);

        if (mapBindingResult.hasErrors()) {
            List<ValidationException.Error> errorsList = getErrorsList(mapBindingResult);
            throw new ValidationException(errorsList);
        }
    }

    private List<ValidationException.Error> getErrorsList(MapBindingResult mapBindingResult) {
        return mapBindingResult.getAllErrors().stream().map(this::mapError).collect(toList());
    }

    private ValidationException.Error mapError(ObjectError error) {
        if (error instanceof FieldError) {
            return getFieldError(error);
        } else {
            return getGeneralError(error);
        }
    }

    private ValidationException.Error getFieldError(ObjectError error) {
        String field = ((FieldError) error).getField();
        String code = error.getDefaultMessage();
        return ValidationException.Error.builder()
                .field(field)
                .code(code)
                .build();
    }

    private ValidationException.Error getGeneralError(ObjectError error) {
        return ValidationException.Error.builder()
                .code(error.getDefaultMessage())
                .build();
    }
}
