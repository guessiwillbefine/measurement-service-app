package ua.ms.journal.service.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ua.ms.journal.entity.AlertEvent;
import ua.ms.journal.entity.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<AlertEvent, ObjectId> {
    List<AlertEvent> findAllByEventType(EventType eventType);
    @Query("{ 'timestamp': { $gt: ?0, $lt: ?1 } }")
    List<AlertEvent> gatAllEventsInBetween(LocalDateTime from, LocalDateTime to);
    AlertEvent findTopBySensorIdAndEventTypeOrderByIdDesc(long sensorId, EventType eventType);
}
