package ua.ms.util.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.Factory;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.service.FactoryService;

import java.util.Optional;

@Component("FactoryMapper")
@RequiredArgsConstructor
public class FactoryMapper implements Mapper<Factory, FactoryDto> {
    private final FactoryService factoryService;

    @Override
    public Factory toEntity(FactoryDto dto) {
        Optional<Factory> byName = factoryService.findByName(dto.getName(), Factory.class);
        return byName.orElseThrow();
    }

    @Override
    public FactoryDto toDto(Factory entity) {
        return FactoryDto.builder()
                .id(entity.getId())
                .name(entity.getName()).build();
    }
}
