package ua.ms.entity.work_shift.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import ua.ms.entity.work_shift.AbstractWorkShiftIdentifiable;

@Getter
@Builder
@ToString
@Jacksonized
public class WorkShiftDto implements AbstractWorkShiftIdentifiable {
    @NotNull
    private Long machineId;
    @NotNull
    private Long workerId;
}
