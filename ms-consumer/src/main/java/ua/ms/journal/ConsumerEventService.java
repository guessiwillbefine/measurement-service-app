package ua.ms.journal;

import org.springframework.stereotype.Service;
import ua.ms.journal.entity.Event;
import ua.ms.journal.entity.EventType;
import ua.ms.journal.service.EventJournalService;
import ua.ms.journal.service.repo.EventRepository;
@Service
public class ConsumerEventService extends EventJournalService {
    protected ConsumerEventService(EventRepository eventRepository) {
        super(eventRepository);
    }

    public void saveConsumedEvent(long sensorId) {
        Event event = new Event();
        event.setEventType(EventType.ALERT_CONSUMED);
        event.setSensorId(sensorId);
        saveEvent(event);

    }
    public void saveSentEvent(long sensorId) {
        Event event = new Event();
        event.setEventType(EventType.ALERT_SENT);
        event.setSensorId(sensorId);
        saveEvent(event);
    }

    public void saveSentSkippedEvent(long sensorId) {
        Event event = new Event();
        event.setEventType(EventType.ALERT_SKIPPED);
        event.setSensorId(sensorId);
        saveEvent(event);
    }
    public Event getLastSendEventBySensorId(long sensorId, EventType eventType) {
        return dao.findTopBySensorIdAndEventTypeOrderByIdDesc(sensorId, eventType);
    }
}
