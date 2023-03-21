package ua.ms.util.exception.response.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.util.exception.EntityValidationException;
import ua.ms.util.exception.response.ExceptionResponseWrapper;
import ua.ms.util.exception.response.dto.ValidationErrorsDto;

import java.util.List;
@Component
@NoArgsConstructor
public class ValidationErrorResponse implements ExceptionResponseWrapper<List<ValidationErrorsDto>, EntityValidationException> {
    private List<ValidationErrorsDto> response;

    private ValidationErrorResponse(List<ValidationErrorsDto> response) {
        this.response = response;
    }
    @Override
    public ExceptionResponseWrapper<List<ValidationErrorsDto>, EntityValidationException> of(EntityValidationException e) {
            List<ValidationErrorsDto> errors = e.getBindingResult().getFieldErrors().stream()
                    .map(error -> ValidationErrorsDto.builder()
                            .field(error.getField())
                            .errors(List.of(new ValidationErrorsDto.Error(error.getDefaultMessage())))
                            .build())
                    .toList();
            return new ValidationErrorResponse(errors);
    }

    @Override
    public List<ValidationErrorsDto> getResponse() {
        return response;
    }
}
