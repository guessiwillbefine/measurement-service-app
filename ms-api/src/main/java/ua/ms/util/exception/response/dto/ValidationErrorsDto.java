package ua.ms.util.exception.response.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Builder
public class ValidationErrorsDto {
    private String field;
    private List<Error> errors;
    public record Error(String text){}
}
