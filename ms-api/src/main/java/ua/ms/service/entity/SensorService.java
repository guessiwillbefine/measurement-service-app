package ua.ms.service.entity;

import org.springframework.data.domain.Pageable;
import ua.ms.entity.sensor.AbstractSensorIdentifiable;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;

import java.util.List;

public interface SensorService extends IdentifiableService<AbstractSensorIdentifiable> {

    <T extends AbstractSensorIdentifiable> List<T> findAll(Pageable pagination, Class<T> type);


    Sensor update(long id, SensorDto sensorDto);

    Sensor delete(long id);

    <T extends AbstractSensorIdentifiable> List<T> findAll(Long machineId, Class<T> type);

    Sensor create(Sensor sensorToCreate);
}
