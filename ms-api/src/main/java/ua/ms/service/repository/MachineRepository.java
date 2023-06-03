package ua.ms.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ms.entity.machine.AbstractMachineIdentifiable;
import ua.ms.entity.machine.Machine;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    <T extends AbstractMachineIdentifiable> Optional<T> findById(long id, Class<T> type);
    <T extends AbstractMachineIdentifiable> List<T> findBy(Pageable pageable, Class<T> type);
    @Query("SELECT m FROM Machine m INNER JOIN m.sensors s WHERE s.id = :sensorId")
    <T extends AbstractMachineIdentifiable> Optional<T> findBySensor(@Param("sensorId") long sensorId, Class<T> type);
}
