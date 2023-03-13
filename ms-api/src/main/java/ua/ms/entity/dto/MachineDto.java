package ua.ms.entity.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.entity.MachineType;
import ua.ms.util.ApplicationConstants.Validation;

@Builder
@Jacksonized
@Getter
@Setter
public class MachineDto {
    @Nullable
    private Long id;
    @NotEmpty
    @Length(max = Validation.MAX_MACHINE_NAME_LENGTH,
            message = Validation.MACHINE_NAME_MSG)
    private String name;
    @NotEmpty
    @Length(max = Validation.MAX_MACHINE_MODEL_LENGTH,
            message = Validation.MACHINE_MODEL_MSG)
    private String model;
    @Nullable
    private MachineType type;
}
