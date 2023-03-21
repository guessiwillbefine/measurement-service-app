package ua.ms.util.exception.response;

import lombok.Getter;
import ua.ms.util.exception.EntityValidationException;
import ua.ms.util.exception.response.dto.ValidationErrorsDto;

import java.util.List;
@Getter
public class ValidationErrorResponse {
    private List<ValidationErrorsDto> response;

    public ValidationErrorResponse(List<ValidationErrorsDto> response) {
        this.response = response;
    }

    public static ValidationErrorResponse of(EntityValidationException e) {
        List<ValidationErrorsDto> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> ValidationErrorsDto.builder()
                        .field(error.getField())
                        .errors(List.of(new ValidationErrorsDto.Error(error.getDefaultMessage())))
                        .build())
                .toList();
        return new ValidationErrorResponse(errors);
    }
}
