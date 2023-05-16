package ua.ms.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.machine.dto.MachineDto;
import ua.ms.util.mapper.Mapper;

@Component("MachineMapper")
@RequiredArgsConstructor
public class MachineMapper implements Mapper<Machine, MachineDto> {

    @Override
    public Machine toEntity(MachineDto dto) {
        return Machine.builder()
                .id(dto.getId())
                .type(dto.getType())
                .factory(Factory.builder().id(dto.getFactoryId()).build())
                .name(dto.getName())
                .model(dto.getModel())
                .factory(Factory.builder().id(dto.getFactoryId()).build())
                .build();
    }

    @Override
    public MachineDto toDto(Machine entity) {
        return MachineDto.builder()
                .name(entity.getName())
                .model(entity.getModel())
                .id(entity.getId())
                .type(entity.getType())
                .activity(entity.getActivity())
                .factoryId(entity.getFactory().getId())
                .build();
    }
}
