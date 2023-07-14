package ua.ms.service.entity;

import ua.ms.entity.factory.AbstractFactoryIdentifiable;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.factory.dto.FactoryDto;

import java.util.List;
import java.util.Optional;

public interface FactoryService extends IdentifiableService<AbstractFactoryIdentifiable> {

    Factory save(Factory factory);

    Factory deleteById(long id);

    Factory update(long id, FactoryDto factoryDto);

    <T extends AbstractFactoryIdentifiable> Optional<T> findByName(String name, Class<T> type);

    <T extends AbstractFactoryIdentifiable> List<T> findAll(Class<T> type);
}
