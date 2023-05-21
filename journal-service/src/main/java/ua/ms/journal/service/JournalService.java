package ua.ms.journal.service;

import ua.ms.journal.entity.Event;
import ua.ms.journal.entity.EventType;

import java.time.LocalDateTime;
import java.util.List;

/** Journal service. Get access to MongoDB for saving events */
interface JournalService {

    void saveEvent(Event event);

    List<Event> getAllEventsByType(EventType eventType);

    List<Event> getEventsFromTo(LocalDateTime from, LocalDateTime to);
}
