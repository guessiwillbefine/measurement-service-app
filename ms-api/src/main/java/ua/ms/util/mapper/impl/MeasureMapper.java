package ua.ms.util.mapper.impl;

import org.springframework.stereotype.Component;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.measure.dto.MeasureDto;
import ua.ms.util.mapper.Mapper;

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
