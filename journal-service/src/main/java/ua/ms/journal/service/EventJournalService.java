package ua.ms.journal.service;

import lombok.RequiredArgsConstructor;
import ua.ms.journal.entity.Event;
import ua.ms.journal.entity.EventType;
import ua.ms.journal.service.repo.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public abstract class EventJournalService implements JournalService {

    private final EventRepository eventRepository;

    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEventsByType(EventType eventType) {
        return eventRepository.findAllByEventType(eventType);
    }

    @Override
    public List<Event> getEventsFromTo(LocalDateTime from, LocalDateTime to) {
       return eventRepository.gatAllEventsInBetween(from, to);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public EventRepository getDao() {
        return eventRepository;
    }
}
