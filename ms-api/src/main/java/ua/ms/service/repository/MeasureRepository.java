package ua.ms.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ms.entity.measure.AbstractMeasureIdentifiable;
import ua.ms.entity.measure.Measure;

import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {
    <T extends AbstractMeasureIdentifiable> List<T> findBySensorIdOrderByCreatedAtDesc(long id, Class<T> type);
    List<Measure> deleteBySensorId(long id);

    @Query("""
                FROM Measure m WHERE m.sensor.id = :id
                AND m.createdAt = (SELECT MAX(m2.createdAt)
                FROM Measure m2 WHERE m2.sensor.id = :id)
            """)
    <T extends AbstractMeasureIdentifiable> T getLastMeasure(@Param("id") long sensorId, Class<T> type);
}
