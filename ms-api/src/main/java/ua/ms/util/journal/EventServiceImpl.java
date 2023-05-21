package ua.ms.util.journal;

import org.springframework.stereotype.Service;
import ua.ms.journal.entity.Event;
import ua.ms.journal.service.EventJournalService;
import ua.ms.journal.entity.EventType;
import ua.ms.journal.service.repo.EventRepository;

@Service("eventJournalService")
public class EventServiceImpl extends EventJournalService {
    public EventServiceImpl(EventRepository eventRepository) {
        super(eventRepository);

    }
    public void saveAuthorizationEvent(){
        Event authorizationEvent = new Event();
        authorizationEvent.setEventType(EventType.AUTHORIZATION);
        saveEvent(authorizationEvent);
    }
}
