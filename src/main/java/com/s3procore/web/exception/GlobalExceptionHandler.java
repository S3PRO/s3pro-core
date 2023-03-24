package com.s3procore.web.exception;

import com.s3procore.dto.error.ErrorDto;
import com.s3procore.dto.error.ErrorResultDto;
import com.s3procore.dto.validation.ValidationErrorDto;
import com.s3procore.dto.validation.ValidationResultDto;
import com.s3procore.service.exception.AuthenticationException;
import com.s3procore.service.exception.EntityAlreadyExistsException;
import com.s3procore.service.exception.GenericException;
import com.s3procore.service.exception.ObjectNotFoundException;
import com.s3procore.service.exception.RelatedObjectNotFoundException;
import com.s3procore.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ValidationResultDto> handleValidationException(Exception ex) {
        List<ValidationErrorDto> errors = null;

        if (ex instanceof ValidationException) {
            ValidationException validationException = (ValidationException) ex;
            errors = validationException.getErrors().stream()
                    .map(error -> new ValidationErrorDto(error.getField(), error.getCode()))
                    .collect(toList());
        } else if (ex instanceof EntityAlreadyExistsException) {
            errors = List.of(new ValidationErrorDto(null, "ENTITY_ALREADY_EXISTS"));
        }

        ValidationResultDto validationResultDto = new ValidationResultDto(errors);

        return ResponseEntity.badRequest().body(validationResultDto);
    }

    @ExceptionHandler(RelatedObjectNotFoundException.class)
    public ResponseEntity<Object> handleRelatedObjectNotFoundException(RelatedObjectNotFoundException ex) {
        String errorMessage = ex.isMessagePresent()
                ? ex.getMessage()
                : String.format("Related object not found [id=%s, objectType=%s]", ex.getId(), ex.getObjectClass());

        log.warn(errorMessage, ex);

        List<ErrorDto> errors = List.of(new ErrorDto("RELATED_OBJECT_NOT_FOUND", errorMessage));

        ErrorResultDto errorResultDto = new ErrorResultDto(errors);

        return ResponseEntity.badRequest().body(errorResultDto);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex) {
        String errorMessage = ex.isMessagePresent()
                ? ex.getMessage()
                : String.format("Object not found [id=%s, objectType=%s]", ex.getId(), ex.getObjectClass());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorResultDto> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication exception: [errorCode={}, message={}]", ex.getErrorCode(), ex.getMessage());

        List<ErrorDto> errors = List.of(new ErrorDto(ex.getErrorCode(), ex.getMessage()));

        ErrorResultDto errorResultDto = new ErrorResultDto(errors);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResultDto);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ValidationResultDto> handleValidationException(ValidationException ex) {
        List<ValidationErrorDto> errors = ex.getErrors().stream()
                .map(error -> new ValidationErrorDto(error.getField(), error.getCode()))
                .collect(toList());
        ValidationResultDto validationResultDto = new ValidationResultDto(errors);
        return ResponseEntity.badRequest().body(validationResultDto);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResultDto> handleGenericException(GenericException ex) {
        log.error("Generic exception [errorCode={}, details={}]", ex.getErrorCode(), ex.getDetails());

        List<ErrorDto> errors = List.of(new ErrorDto(ex.getErrorCode(), ex.getDetails()));

        ErrorResultDto errorResultDto = new ErrorResultDto(errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResultDto);
    }
}
