package ua.ms.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.factory.dto.FactoryDto;
import ua.ms.util.mapper.Mapper;

@Component("FactoryMapper")
@RequiredArgsConstructor
public class FactoryMapper implements Mapper<Factory, FactoryDto> {

    @Override
    public Factory toEntity(FactoryDto dto) {
        return Factory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public FactoryDto toDto(Factory entity) {
        return FactoryDto.builder()
                .id(entity.getId())
                .name(entity.getName()).build();
    }
}
