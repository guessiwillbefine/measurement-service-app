package ua.ms.journal.service.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ua.ms.journal.entity.Event;
import ua.ms.journal.entity.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findAllByEventType(EventType eventType);
    @Query("{ 'timestamp': { $gt: ?0, $lt: ?1 } }")
    List<Event> gatAllEventsInBetween(LocalDateTime from, LocalDateTime to);
    Event findTopBySensorIdAndEventTypeOrderByIdDesc(long sensorId, EventType eventType);
}
