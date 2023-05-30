package ua.ms.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    <T extends AbstractWorkShiftIdentifiable> Optional<T> findByWorkerIdAndMachineIdAndEndedInIsNull(Long workerId, Long machineId, Class<T> type);

    @Query("""
                FROM WorkShift w
                where w.machine.id = :machineId
                and w.machine.activity = 'ACTIVE'
                and w.endedIn = null
            """)
    <T extends AbstractWorkShiftIdentifiable> Optional<T> findByMachine(@Param("machineId") long machineId, Class<T> type);
}
