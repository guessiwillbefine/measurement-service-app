package ua.ms.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.sensor.AbstractSensorIdentifiable;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.service.entity.SensorService;
import ua.ms.service.repository.SensorRepository;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    @Override
    @Transactional(readOnly = true)
    public <T extends AbstractSensorIdentifiable> List<T> findAll(Pageable pagination, Class<T> type) {
        return sensorRepository.findBy(pagination, type);
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends AbstractSensorIdentifiable> Optional<T> findById(long id, Class<T> type) {
        return sensorRepository.findById(id, type);
    }

    @Override
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

    @Override
    @Transactional
    public Sensor delete(long id) {
        Optional<Sensor> byId = sensorRepository.findById(id);
        if (byId.isEmpty())
            throw new EntityNotFoundException("Sensor is not found");

        sensorRepository.delete(byId.get());
        return byId.get();
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends AbstractSensorIdentifiable> List<T> findAll(Long machineId, Class<T> type) {
        return sensorRepository.findByMachineId(machineId, type);
    }

    @Override
    @Transactional
    public Sensor create(Sensor sensorToCreate) {
        return sensorRepository.save(sensorToCreate);
    }
}
