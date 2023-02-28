package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.Factory;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.service.repository.FactoryRepository;
import ua.ms.util.exception.FactoryDuplicateException;
import ua.ms.util.exception.FactoryNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class FactoryService {
    private final FactoryRepository factoryRepository;

    @Transactional(readOnly = true)
    public <T> Optional<T> findById(long id, Class<T> type) {
        return factoryRepository.findById(id, type);
    }

    @Transactional
    public Factory save(FactoryDto factoryDto) {
        Optional<Factory> byName = factoryRepository.findByName(factoryDto.getName(), Factory.class);
        if (byName.isEmpty()) {
                Factory factoryToSave = Factory.builder()
                        .name(factoryDto.getName())
                        .build();
                return factoryRepository.save(factoryToSave);
        }
        throw new FactoryDuplicateException(format("Factory[%s] is already exists", factoryDto.getName()));
    }

    @Transactional
    public Factory deleteById(long id) {
        Optional<Factory> byId = factoryRepository.findById(id, Factory.class);
        if (byId.isPresent()) {
            factoryRepository.deleteById(id);
            return byId.get();
        }
        throw new FactoryNotFoundException(format("Factory with id[%d] wasn't found", id));
    }

    @Transactional
    public Factory update(long id, FactoryDto factoryDto) {
        Optional<Factory> byId = factoryRepository.findById(id, Factory.class);
        if (byId.isPresent()) {
            Factory toUpdate = updateEntity(byId.get(), factoryDto);
            return factoryRepository.save(toUpdate);
        }
        throw new FactoryNotFoundException(format("Factory with id[%d] wasn't found", id));
    }

    @Transactional(readOnly = true)
    public <T> Optional<T> findByName(String name, Class<T> type) {
        return factoryRepository.findByName(name, type);
    }

    private Factory updateEntity(Factory factory, FactoryDto factoryDto) {
        return Factory.builder()
                .name(factoryDto.getName() == null ? factory.getName() : factoryDto.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public <T> List<T> findAll(Class<T> type) {
        return factoryRepository.findBy(type);
    }
}
