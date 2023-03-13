package ua.ms.util.mapper;

import org.springframework.stereotype.Component;
import ua.ms.entity.Measure;
import ua.ms.entity.Sensor;
import ua.ms.entity.dto.MeasureDto;

@Component
public class MeasureMapper implements Mapper<Measure, MeasureDto> {
    @Override
    public Measure toEntity(MeasureDto dto) {
        return Measure.builder()
                .sensor(Sensor.builder()
                        .id(dto.getSensorId())
                        .build())
                .value(dto.getValue())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    @Override
    public MeasureDto toDto(Measure entity) {
        return MeasureDto.builder()
                .id(entity.getId())
                .value(entity.getValue())
                .sensorId(entity.getSensor() == null? 0: entity.getSensor().getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
