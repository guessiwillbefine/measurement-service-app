package ua.ms.controller.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.*;
import ua.ms.util.exception.response.ExceptionResponse;
import ua.ms.util.exception.response.ValidationErrorResponse;
import ua.ms.util.exception.response.dto.ValidationErrorsDto;

import java.util.List;

@RestControllerAdvice
public class EntityExceptionHandler {
    @ExceptionHandler({EntityValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationErrorsDto> validationExceptionResponse(EntityValidationException e) {
        return ValidationErrorResponse.of(e).getResponse();
    }

    @ExceptionHandler({EntityDuplicateException.class})
    public ResponseEntity<ExceptionResponse> duplicateExceptionResponse(RuntimeException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionResponse> notFoundResponse(RuntimeException e) {
        EntityValidationException exception = (EntityValidationException) e;
        return ResponseEntity.status(404).body(ExceptionResponse.of(exception));
    }
}
