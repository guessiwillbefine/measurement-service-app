package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.Machine;
import ua.ms.entity.dto.MachineDto;
import ua.ms.service.repository.MachineRepository;
import ua.ms.util.exception.MachineNotFoundException;
import ua.ms.util.mapper.impl.MachineMapper;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class MachineService {
    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;

    @Transactional(readOnly = true)
    public <T> Optional<T> findById(final Long id, final Class<T> type) {
       return machineRepository.findById(id, type);
    }

    @Transactional(readOnly = true)
    public <T> List<T> findAll(final Pageable pageable, final Class<T> type) {
        return machineRepository.findBy(pageable, type);
    }

    @Transactional
    public Machine save(final MachineDto machineDto) {
        return machineRepository.save(machineMapper.toEntity(machineDto));
    }

    @Transactional
    public MachineDto update(final Long id, final MachineDto machineDto) {
        Optional<Machine> byId = machineRepository.findById(id);
        if (byId.isPresent()) {
            Machine updated = updateEntity(byId.get(), machineDto);
            machineRepository.save(updated);
            return machineDto;
        }
        throw new MachineNotFoundException(format("Machine with id[%d] not found", id));
    }
    private Machine updateEntity(final Machine machine, final MachineDto machineDto) {
        machine.setType(machineDto.getType());
        machine.setName(machineDto.getName());
        machine.setModel(machineDto.getModel());
        return machine;
    }

    @Transactional
    public MachineDto delete(final Long id) {
        Optional<MachineDto> byId = machineRepository.findById(id, MachineDto.class);
        if (byId.isPresent()) {
            machineRepository.deleteById(id);
            return byId.get();
        }
        throw new MachineNotFoundException(format("Machine with id[%d] not found", id));
    }
}
