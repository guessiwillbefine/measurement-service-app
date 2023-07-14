package ua.ms.service.entity;

import org.springframework.data.domain.Pageable;
import ua.ms.entity.machine.AbstractMachineIdentifiable;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.machine.dto.MachineDto;

import java.util.List;
import java.util.Optional;

public interface MachineService extends IdentifiableService<AbstractMachineIdentifiable> {

    <T extends AbstractMachineIdentifiable> List<T> findAll(final Pageable pageable, final Class<T> type);

    Machine save(final Machine machine);

    MachineDto update(final Long id, final MachineDto machineDto);

    MachineDto delete(final Long id);

    <T extends AbstractMachineIdentifiable> List<T> getMachinesByIds(Class<T> type, List<Long> ids);

    <T extends AbstractMachineIdentifiable> Optional<T> findBySensorId(long sensorId, Class<T> type);
}
