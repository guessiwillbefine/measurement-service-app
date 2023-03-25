package ua.ms.entity.measure.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;
import ua.ms.entity.measure.AbstractMeasureIdentifiable;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Jacksonized
public class MeasureDto implements AbstractMeasureIdentifiable {
    @Nullable
    private Long id;
    @NotNull
    private double value;

    @NotNull
    @Min(1)
    private long sensorId;

    @Nullable
    private LocalDateTime createdAt;
}
