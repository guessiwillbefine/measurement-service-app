package ua.ms.util.exception.response.impl;

import org.springframework.stereotype.Component;
import ua.ms.util.exception.EntityValidationException;
import ua.ms.util.exception.response.ExceptionResponseWrapper;
import ua.ms.util.exception.response.dto.ValidationErrorsDto;

import java.util.List;

@Component
public class ValidationErrorResponse implements ExceptionResponseWrapper<List<ValidationErrorsDto>, EntityValidationException> {
    private List<ValidationErrorsDto> response;
    @Override
    public ExceptionResponseWrapper<List<ValidationErrorsDto>, EntityValidationException> of(EntityValidationException e) {
           this.response = e.getBindingResult().getFieldErrors().stream()
                    .map(error -> ValidationErrorsDto.builder()
                            .field(error.getField())
                            .errors(List.of(new ValidationErrorsDto.Error(error.getDefaultMessage())))
                            .build())
                    .toList();
            return this;
    }

    @Override
    public List<ValidationErrorsDto> getResponse() {
        return response;
    }
}
