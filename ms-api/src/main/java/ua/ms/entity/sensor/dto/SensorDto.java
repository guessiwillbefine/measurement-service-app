package ua.ms.entity.sensor.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.entity.measure.MeasureSystem;
import ua.ms.entity.sensor.AbstractSensorIdentifiable;
import ua.ms.util.ApplicationConstants;

@Getter
@Builder
@ToString
@Jacksonized
public class SensorDto implements AbstractSensorIdentifiable {

    @Nullable
    private Long id;

    @Length(min = ApplicationConstants.Validation.MIN_NAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_NAME_LENGTH,
            message = ApplicationConstants.Validation.SENSOR_NAME_MSG)
    private String name;

    @NotNull
    private MeasureSystem measureSystem;

    private Long machineId;

    @Nullable
    private Double criticalValue;
}
