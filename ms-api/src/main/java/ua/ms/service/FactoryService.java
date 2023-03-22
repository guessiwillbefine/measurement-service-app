package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.factory.AbstractFactoryIdentifiable;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.factory.dto.FactoryDto;
import ua.ms.service.repository.FactoryRepository;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class FactoryService {
    private final FactoryRepository factoryRepository;

    @Transactional(readOnly = true)
    public <T extends AbstractFactoryIdentifiable> Optional<T> findById(long id, Class<T> type) {
        return factoryRepository.findById(id, type);
    }

    @Transactional
    public Factory save(Factory factory) {
        try {
            return factoryRepository.save(factory);
        } catch (DataIntegrityViolationException e) {
            throw new EntityDuplicateException(format("Factory[%s] is already exists", factory.getName()));
        }
    }

    @Transactional
    public Factory deleteById(long id) {
        Optional<Factory> byId = factoryRepository.findById(id, Factory.class);
        if (byId.isPresent()) {
            factoryRepository.deleteById(id);
            return byId.get();
        }
        throw new EntityNotFoundException(format("Factory with id[%d] wasn't found", id));
    }

    @Transactional
    public Factory update(long id, FactoryDto factoryDto) {
        Optional<Factory> byId = factoryRepository.findById(id, Factory.class);
        if (byId.isPresent()) {
            Factory toUpdate = updateEntity(byId.get(), factoryDto);
            return factoryRepository.save(toUpdate);
        }
        throw new EntityNotFoundException(format("Factory with id[%d] wasn't found", id));
    }

    private Factory updateEntity(Factory factory, FactoryDto factoryDto) {
        return Factory.builder()
                .name(factoryDto.getName() == null ? factory.getName() : factoryDto.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public <T extends AbstractFactoryIdentifiable> Optional<T> findByName(String name, Class<T> type) {
        return factoryRepository.findByName(name, type);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractFactoryIdentifiable> List<T> findAll(Class<T> type) {
        return factoryRepository.findBy(type);
    }
}
