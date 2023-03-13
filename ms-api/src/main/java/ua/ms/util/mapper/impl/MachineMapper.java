package ua.ms.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.Machine;
import ua.ms.entity.dto.MachineDto;
import ua.ms.service.MachineService;
import ua.ms.util.mapper.Mapper;

import java.util.Optional;

@Component("MachineMapper")
@RequiredArgsConstructor
public class MachineMapper implements Mapper<Machine, MachineDto> {
    private final MachineService machineService;

    @Override
    public Machine toEntity(MachineDto dto) {
        Optional<Machine> byId = machineService.findById(dto.getId(), Machine.class);
        return byId.orElseThrow();
    }

    @Override
    public MachineDto toDto(Machine entity) {
        return MachineDto.builder().name(entity.getName())
                .model(entity.getModel())
                .id(entity.getId())
                .type(entity.getType()).build();
    }
}
