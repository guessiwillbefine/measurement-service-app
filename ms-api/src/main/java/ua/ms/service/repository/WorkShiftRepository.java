package ua.ms.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ms.entity.work_shift.AbstractWorkShiftIdentifiable;
import ua.ms.entity.work_shift.WorkShift;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {
    <T extends AbstractWorkShiftIdentifiable> List<T> findBy(Pageable pageable, Class<T> type);
    <T extends AbstractWorkShiftIdentifiable> List<T> findByWorkerId(Long id, Pageable pageable, Class<T> type);
    <T extends AbstractWorkShiftIdentifiable> Optional<T> findByIdAndWorkerId(Long workShiftId, Long workerId, Class<T> type);

}
