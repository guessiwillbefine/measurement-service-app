package ua.ms.journal.service;

import ua.ms.journal.entity.EventType;
import ua.ms.journal.entity.MongoEntity;

import java.time.LocalDateTime;
import java.util.List;

/** Journal service. Get access to MongoDB for saving events */
interface JournalService<E extends MongoEntity> {

    void saveEvent(E event);

    List<E> getAllEventsByType(EventType eventType);

    List<E> getEventsFromTo(LocalDateTime from, LocalDateTime to);
}
