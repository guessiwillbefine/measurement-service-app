package ua.ms.entity.machine.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.entity.machine.AbstractMachineIdentifiable;
import ua.ms.entity.machine.MachineActivity;
import ua.ms.entity.machine.MachineType;
import ua.ms.util.ApplicationConstants.Validation;

@Builder
@Jacksonized
@Getter
@Setter
public class MachineDto implements AbstractMachineIdentifiable {
    @Nullable
    private Long id;
    @NotEmpty(message = Validation.MACHINE_NAME_MSG)
    @Length(max = Validation.MAX_MACHINE_NAME_LENGTH,
            message = Validation.MACHINE_NAME_MSG)
    private String name;
    @NotEmpty(message = Validation.MACHINE_NAME_MSG)
    @Length(max = Validation.MAX_MACHINE_MODEL_LENGTH,
            message = Validation.MACHINE_MODEL_MSG)
    private String model;
    @Nullable
    private MachineType type;
    @Nullable
    private MachineActivity activity;
    @Min(value = 1, message = Validation.MACHINE_FACTORY_MSG)
    private Long factoryId;
}
