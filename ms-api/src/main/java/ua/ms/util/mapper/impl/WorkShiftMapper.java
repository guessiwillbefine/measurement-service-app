package ua.ms.util.mapper.impl;

import org.springframework.stereotype.Component;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.user.User;
import ua.ms.entity.work_shift.WorkShift;
import ua.ms.entity.work_shift.dto.WorkShiftDto;
import ua.ms.util.mapper.Mapper;

@Component
public class WorkShiftMapper implements Mapper<WorkShift, WorkShiftDto> {
    @Override
    public WorkShift toEntity(WorkShiftDto dto) {
        return WorkShift.builder()
                .worker(User.builder()
                        .id(dto.getWorkerId())
                        .build())
                .machine(Machine.builder()
                        .id(dto.getMachineId())
                        .build())
                .build();
    }

    @Override
    public WorkShiftDto toDto(WorkShift entity) {
        return WorkShiftDto.builder()
                .workerId(entity.getWorker().getId())
                .machineId(entity.getMachine().getId())
                .build();
    }
}
