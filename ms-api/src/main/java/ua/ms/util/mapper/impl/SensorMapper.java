package ua.ms.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.util.mapper.Mapper;

@Component
@RequiredArgsConstructor
public class SensorMapper implements Mapper<Sensor, SensorDto> {

    @Override
    public Sensor toEntity(SensorDto dto) {
        return Sensor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .measureSystem(dto.getMeasureSystem())
                .criticalValue(dto.getCriticalValue())
                .machine(Machine.builder().id(dto.getMachineId()).build())
                .build();
    }

    @Override
    public SensorDto toDto(Sensor entity) {
        return SensorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .criticalValue(entity.getCriticalValue())
                .measureSystem(entity.getMeasureSystem())
                .machineId(entity.getMachine().getId())
                .build();
    }
}
