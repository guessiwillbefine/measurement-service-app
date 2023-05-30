package ua.ms.journal.service;

import ua.ms.journal.entity.AlertEvent;
import ua.ms.journal.entity.EventType;
import ua.ms.journal.service.repo.EventRepository;
import java.time.LocalDateTime;
import java.util.List;

public abstract class EventJournalService extends AbstractJournalService<EventRepository, AlertEvent> {

    protected EventJournalService(EventRepository eventRepository) {
        super(eventRepository);
    }

    @Override
    public List<AlertEvent> getAllEventsByType(EventType eventType) {
        return dao.findAllByEventType(eventType);
    }

    @Override
    public List<AlertEvent> getEventsFromTo(LocalDateTime from, LocalDateTime to) {
       return dao.gatAllEventsInBetween(from, to);
    }

    public List<AlertEvent> findAll() {
        return dao.findAll();
    }
}
