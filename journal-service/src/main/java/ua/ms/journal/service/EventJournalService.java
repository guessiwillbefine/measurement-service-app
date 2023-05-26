package ua.ms.journal.service;

import ua.ms.journal.entity.Event;
import ua.ms.journal.entity.EventType;
import ua.ms.journal.service.repo.EventRepository;
import java.time.LocalDateTime;
import java.util.List;

public abstract class EventJournalService extends AbstractJournalService<EventRepository, Event> {

    protected EventJournalService(EventRepository eventRepository) {
        super(eventRepository);
    }

    @Override
    public void saveEvent(Event event) {
        dao.save(event);
    }

    @Override
    public List<Event> getAllEventsByType(EventType eventType) {
        return dao.findAllByEventType(eventType);
    }

    @Override
    public List<Event> getEventsFromTo(LocalDateTime from, LocalDateTime to) {
       return dao.gatAllEventsInBetween(from, to);
    }

    public List<Event> findAll() {
        return dao.findAll();
    }
}
