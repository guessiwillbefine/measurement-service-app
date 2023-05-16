package ua.ms.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.sensor.AbstractSensorIdentifiable;
import ua.ms.entity.sensor.Sensor;


import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    <T extends AbstractSensorIdentifiable> List<T> findByMachine(Machine machine, Class<T> type);
    <T extends AbstractSensorIdentifiable> List<T> findBy(Pageable pagination, Class<T> type);
    <T extends AbstractSensorIdentifiable> Optional<T> findById(long id, Class<T> type);
    <T extends AbstractSensorIdentifiable> Optional<T> findByName(String name, Class<T> type);
    Sensor findFirstById(long id);

}
