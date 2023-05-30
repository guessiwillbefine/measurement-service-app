package ua.ms.util.journal;

import org.springframework.stereotype.Service;
import ua.ms.journal.entity.AlertEvent;
import ua.ms.journal.entity.AuthenticationEvent;
import ua.ms.journal.service.EventJournalService;
import ua.ms.journal.entity.EventType;
import ua.ms.journal.service.repo.AuthorizationEventRepository;
import ua.ms.journal.service.repo.EventRepository;

@Service("eventJournalService")
public class EventServiceImpl extends EventJournalService {

    private final AuthorizationEventRepository authorizationEventRepository;

    public EventServiceImpl(EventRepository eventRepository,
                            AuthorizationEventRepository authorizationEventRepository) {
        super(eventRepository);
        this.authorizationEventRepository = authorizationEventRepository;
    }

    public void saveAuthorizationEvent(String fullName){
        AuthenticationEvent authorizationAlertEvent = new AuthenticationEvent();
        authorizationAlertEvent.setEventType(EventType.AUTHORIZATION);
        authorizationAlertEvent.setUsername(fullName);
        authorizationEventRepository.save(authorizationAlertEvent);
    }

    public void saveProducingAlertEvent(long sensorId) {
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.setEventType(EventType.ALERT_PUSHED);
        alertEvent.setSensorId(sensorId);
        saveEvent(alertEvent);
    }
}
