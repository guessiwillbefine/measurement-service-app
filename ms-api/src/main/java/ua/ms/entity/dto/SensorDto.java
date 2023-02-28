package ua.ms.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.util.ApplicationConstants;

@Getter
@Builder
@Jacksonized
public class SensorDto {

    private Long id;

    @Length(min = ApplicationConstants.Validation.MIN_NAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_NAME_LENGTH,
            message = ApplicationConstants.Validation.SENSOR_NAME_MSG)
    private String name;
}
