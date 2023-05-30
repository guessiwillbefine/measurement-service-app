package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.machine.MachineActivity;
import ua.ms.entity.user.User;
import ua.ms.entity.work_shift.AbstractWorkShiftIdentifiable;
import ua.ms.entity.work_shift.WorkShift;
import ua.ms.service.repository.WorkShiftRepository;
import ua.ms.util.exception.AccessException;
import ua.ms.util.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkShiftService {
    private final WorkShiftRepository workShiftRepository;
    private final MachineService machineService;

    @Transactional(readOnly = true)
    public <T extends AbstractWorkShiftIdentifiable> List<T> getAll(Pageable pageable, Class<T> type) {
        return workShiftRepository.findBy(pageable, type);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractWorkShiftIdentifiable> List<T> getAllByWorker(Long id, Pageable pageable, Class<T> type) {
        return workShiftRepository.findByWorkerId(id, pageable, type);
    }

    @Transactional
    public WorkShift save(WorkShift workShiftToCreate) {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) ob;
        if (!workShiftToCreate.getWorker().getId().equals(user.getId()))
            throw new AccessException("You don't have access to this action");

        Optional<Machine> byId = machineService.findById(workShiftToCreate.getMachine().getId(), Machine.class);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Machine is not found");
        }

        Machine machine = byId.get();
        if (!machine.getActivity().equals(MachineActivity.INACTIVE)) {
            throw new IllegalStateException("Machine is already taken");
        }

        workShiftToCreate.setStartedAt(LocalDateTime.now());
        WorkShift createdWorkShift = workShiftRepository.save(workShiftToCreate);
        machine.setActivity(MachineActivity.ACTIVE);
        return createdWorkShift;
    }

    @Transactional
    public WorkShift update(WorkShift workShift) {
//        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = (User) ob;

        Optional<WorkShift> workShiftByIdAndUserId = workShiftRepository
                .findByWorkerIdAndMachineIdAndEndedInIsNull(workShift.getWorker().getId(), workShift.getMachine().getId(),
                        WorkShift.class);
        if (workShiftByIdAndUserId.isEmpty()) {
            throw new EntityNotFoundException("Work shift is not found");
        }

        WorkShift workShiftToUpdate = workShiftByIdAndUserId.get();
        Optional<Machine> machineById = machineService.findById(workShiftToUpdate.getMachine().getId(), Machine.class);
        if (machineById.isEmpty()) {
            throw new EntityNotFoundException("Machine is not found");
        }

        Machine machine = machineById.get();
        machine.setActivity(MachineActivity.INACTIVE);
        workShiftToUpdate.setEndedIn(LocalDateTime.now());
        return workShiftRepository.save(workShiftToUpdate);
    }

    @Transactional
    public WorkShift delete(Long id) {
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) ob;

        Optional<WorkShift> workShiftByIdAndUserId = workShiftRepository.findByIdAndWorkerId(id, user.getId(), WorkShift.class);
        if (workShiftByIdAndUserId.isEmpty())
            throw new EntityNotFoundException("This work shift is not found");

        WorkShift workShiftToDelete = workShiftByIdAndUserId.get();
        workShiftRepository.delete(workShiftToDelete);
        return workShiftToDelete;
    }

    @Transactional
    public Optional<WorkShift> getWorkerByMachine(Machine machine) {
        return workShiftRepository.findByMachine(machine.getId(), WorkShift.class);
    }
}
