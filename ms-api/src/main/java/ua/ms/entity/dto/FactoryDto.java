package ua.ms.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;

import static ua.ms.util.ApplicationConstants.Validation;

@Builder
@Jacksonized
@Getter
@Setter
public class FactoryDto {
    private Long id;
    @Length(max = Validation.MAX_FACTORY_NAME_LENGTH,
            message = Validation.FACTORY_NAME_MSG)
    private String name;
}
