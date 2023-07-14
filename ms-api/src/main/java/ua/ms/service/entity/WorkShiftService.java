package ua.ms.service.entity;

import org.springframework.data.domain.Pageable;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.work_shift.AbstractWorkShiftIdentifiable;
import ua.ms.entity.work_shift.WorkShift;

import java.util.List;
import java.util.Optional;

public interface WorkShiftService extends IdentifiableService<AbstractWorkShiftIdentifiable> {

    <T extends AbstractWorkShiftIdentifiable> List<T> getAll(Pageable pageable, Class<T> type);

    <T extends AbstractWorkShiftIdentifiable> List<T> getAllByWorker(Long id, Pageable pageable, Class<T> type);

    WorkShift save(WorkShift workShiftToCreate);

    WorkShift update(WorkShift workShift);

    WorkShift delete(Long id);

    Optional<WorkShift> getWorkerByMachine(Machine machine);
}
