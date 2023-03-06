package ua.ms.util.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.Sensor;
import ua.ms.entity.dto.SensorDto;

@Component
@RequiredArgsConstructor
public class SensorMapper implements Mapper<Sensor, SensorDto>{
    @Override
    public Sensor toEntity(SensorDto dto) {
        return Sensor.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public SensorDto toDto(Sensor entity) {
        return SensorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
