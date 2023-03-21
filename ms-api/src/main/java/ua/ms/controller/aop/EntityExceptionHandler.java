package ua.ms.controller.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.exception.EntityException;
import ua.ms.util.exception.EntityNotFoundException;
import ua.ms.util.exception.EntityValidationException;
import ua.ms.util.exception.response.ExceptionResponseWrapper;
import ua.ms.util.exception.response.dto.ValidationErrorsDto;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class EntityExceptionHandler {
    private final ExceptionResponseWrapper<List<ValidationErrorsDto>, EntityValidationException> validationErrorResponse;
    private final ExceptionResponseWrapper<String, EntityException> entityExceptionExceptionResponse;
    @ExceptionHandler({EntityValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationErrorsDto> validationExceptionResponse(EntityValidationException e) {
        return validationErrorResponse.of(e).getResponse();
    }

    @ExceptionHandler({EntityDuplicateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseWrapper<String, EntityException> duplicateExceptionResponse(EntityException e) {
        return entityExceptionExceptionResponse.of(e);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseWrapper<String, EntityException> notFoundExceptionResponse(EntityException e) {
        return entityExceptionExceptionResponse.of(e);
    }
}
