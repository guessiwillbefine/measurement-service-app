package ua.ms.journal;

import org.springframework.stereotype.Service;
import ua.ms.journal.entity.AlertEvent;
import ua.ms.journal.entity.EventType;
import ua.ms.journal.service.EventJournalService;
import ua.ms.journal.service.repo.EventRepository;
@Service
public class ConsumerEventService extends EventJournalService {
    protected ConsumerEventService(EventRepository eventRepository) {
        super(eventRepository);
    }

    public void saveConsumedEvent(long sensorId) {
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.setEventType(EventType.ALERT_CONSUMED);
        alertEvent.setSensorId(sensorId);
        saveEvent(alertEvent);

    }
    public void saveSentEvent(long sensorId) {
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.setEventType(EventType.ALERT_SENT);
        alertEvent.setSensorId(sensorId);
        saveEvent(alertEvent);
    }

    public void saveSentSkippedEvent(long sensorId) {
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.setEventType(EventType.ALERT_SKIPPED);
        alertEvent.setSensorId(sensorId);
        saveEvent(alertEvent);
    }
    public AlertEvent getLastSendEventBySensorId(long sensorId, EventType eventType) {
        return dao.findTopBySensorIdAndEventTypeOrderByIdDesc(sensorId, eventType);
    }
}
