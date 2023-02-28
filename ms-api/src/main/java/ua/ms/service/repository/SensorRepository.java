package ua.ms.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ms.entity.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
