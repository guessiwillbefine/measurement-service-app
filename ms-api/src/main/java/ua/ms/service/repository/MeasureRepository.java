package ua.ms.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ms.entity.measure.AbstractMeasureIdentifiable;
import ua.ms.entity.measure.Measure;

import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {
    <T extends AbstractMeasureIdentifiable> List<T> findBySensorIdOrderByCreatedAtDesc(long id, Class<T> type);
    List<Measure> deleteBySensorId(long id);
}
