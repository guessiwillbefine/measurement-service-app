package ua.ms.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.machine.AbstractMachineIdentifiable;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.machine.MachineActivity;
import ua.ms.entity.machine.dto.MachineDto;
import ua.ms.service.repository.MachineRepository;
import ua.ms.util.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final EntityManager entityManager;

    private final MachineRepository machineRepository;
    private final FactoryService factoryService;

    @Transactional(readOnly = true)
    public <T extends AbstractMachineIdentifiable> Optional<T> findById(final Long id, final Class<T> type) {
       return machineRepository.findById(id, type);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractMachineIdentifiable> List<T> findAll(final Pageable pageable, final Class<T> type) {
        return machineRepository.findBy(pageable, type);
    }

    @Transactional
    public Machine save(final Machine machine) {
        machine.setActivity(MachineActivity.INACTIVE);
        return machineRepository.save(machine);
    }

    @Transactional
    public MachineDto update(final Long id, final MachineDto machineDto) {
        Optional<Machine> byId = machineRepository.findById(id);
        Optional<Factory> factoryById = factoryService.findById(machineDto.getFactoryId(), Factory.class);
        if (byId.isPresent() && factoryById.isPresent()) {
            Machine updated = updateEntity(byId.get(), machineDto, factoryById.get());
            machineRepository.save(updated);
            return machineDto;
        }
        throw new EntityNotFoundException(format("Machine with id[%d] not found", id));
    }
    private Machine updateEntity(final Machine machine, final MachineDto machineDto, final Factory factory) {
        machine.setType(machineDto.getType());
        machine.setName(machineDto.getName());
        machine.setModel(machineDto.getModel());
        machine.setFactory(factory);
        return machine;
    }

    @Transactional
    public MachineDto delete(final Long id) {
        Optional<MachineDto> byId = machineRepository.findById(id, MachineDto.class);
        if (byId.isPresent()) {
            machineRepository.deleteById(id);
            return byId.get();
        }
        throw new EntityNotFoundException(format("Machine with id[%d] not found", id));

    }

    @Transactional(readOnly = true)
    public <T extends AbstractMachineIdentifiable> List<T> getMachinesByIds(Class<T> type, List<Long> ids) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(type);
        Root<T> root = cr.from(type);
        Expression<Long> idExpression = root.get("id");
        Predicate idPredicate = idExpression.in(ids);
        cr.where(idPredicate);
        return entityManager.createQuery(cr).getResultList();
    }

    @Transactional(readOnly = true)
    public <T extends AbstractMachineIdentifiable> Optional<T> findBySensorId(long sensorId, Class<T> type) {
        System.out.println(sensorId);
        return machineRepository.findBySensor(sensorId, type);
    }
}
