package ua.ms.entity.work_shift.dto.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.ms.entity.machine.dto.view.MachineView;
import ua.ms.entity.user.dto.view.UserView;
import ua.ms.entity.work_shift.AbstractWorkShiftIdentifiable;

import java.time.LocalDateTime;

public interface WorkShiftView extends AbstractWorkShiftIdentifiable {
    Long getId();
    @JsonIgnoreProperties("sensors")
    MachineView getMachine();

    UserView getWorker();

    LocalDateTime getStartedAt();
    LocalDateTime getEndedIn();
}
