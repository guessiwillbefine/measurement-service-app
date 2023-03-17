package ua.ms.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.Factory;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.service.FactoryService;
import ua.ms.util.mapper.Mapper;

import java.util.Optional;

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
