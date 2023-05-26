package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.sensor.AbstractSensorIdentifiable;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.service.repository.SensorRepository;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;

    @Transactional(readOnly = true)
    public <T extends AbstractSensorIdentifiable> List<T> findAll(Pageable pagination, Class<T> type) {
        return sensorRepository.findBy(pagination, type);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractSensorIdentifiable> Optional<T> findOne(long id, Class<T> type) {
        return sensorRepository.findById(id, type);
    }

    @Transactional
    public Sensor update(long id, SensorDto sensorDto) {
        Optional<Sensor> byId = sensorRepository.findById(id);
        if (byId.isEmpty())
            throw new EntityNotFoundException("Sensor is not found");

        Sensor sensorToUpdate = byId.get();
        Sensor updated = updateSensorFields(sensorToUpdate, sensorDto);
        return sensorRepository.save(updated);
    }

    private Sensor updateSensorFields(Sensor sensor, SensorDto sensorDto) {
        sensor.setCriticalValue(sensorDto.getCriticalValue()); //nullable
        sensor.setName(sensorDto.getName() != null ? sensorDto.getName() : sensor.getName());
        sensor.setMeasureSystem(sensorDto.getMeasureSystem() != null ? sensorDto.getMeasureSystem() : sensor.getMeasureSystem());
        return sensor;
    }

    @Transactional
    public Sensor delete(long id) {
        Optional<Sensor> byId = sensorRepository.findById(id);
        if (byId.isEmpty())
            throw new EntityNotFoundException("Sensor is not found");

        sensorRepository.delete(byId.get());
        return byId.get();
    }

    @Transactional(readOnly = true)
    public <T extends AbstractSensorIdentifiable> List<T> findAll(Machine machine, Class<T> type) {
        return sensorRepository.findByMachine(machine, type);
    }

    @Transactional
    public Sensor create(Sensor sensorToCreate) {
        return sensorRepository.save(sensorToCreate);
    }
}
