package ua.ms.util.journal;

import org.springframework.stereotype.Service;
import ua.ms.journalservice.publics.Event;
import ua.ms.journalservice.publics.EventJournalService;
import ua.ms.journalservice.publics.EventType;
import ua.ms.journalservice.repo.EventRepository;

@Service("eventService")
public class EventServiceWrapper extends EventJournalService {
    public EventServiceWrapper(EventRepository eventRepository) {
        super(eventRepository);

    }
    public void saveAuthorizationEvent(){
        Event authorizationEvent = new Event();
        authorizationEvent.setEventType(EventType.AUTHORIZATION);
        saveEvent(authorizationEvent);
    }
}
