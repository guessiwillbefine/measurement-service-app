package ua.ms.consumer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ua.ms.consumer.MessageQueueConsumer;
import ua.ms.journal.ConsumerEventService;
import ua.ms.journal.entity.AlertEvent;
import ua.ms.journal.entity.EventType;
import ua.ms.sender.sender.AbstractMailSender;

import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;


@Log4j2
@Component
@RequiredArgsConstructor
public class MailQueueConsumer implements MessageQueueConsumer<Message<String>> {

    private final ConsumerEventService eventJournalService;

    private final ObjectMapper objectMapper;

    private final AbstractMailSender mailSender;

    @Value("${configuration.mail.timeout}")
    private String resendTimeout;

    @Override
    @RabbitListener(queues = {"${configuration.rabbit.queue-name}"})
    public void consume(Message<String> message) {
        AlertDto alertDto;
        try {
            alertDto = objectMapper.readValue(message.getPayload(), AlertDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
        if (mailSender.isMailSenderInitialized()) {
            log.warn("Email was skipped - email sender properties are not correct");
            //TODO надо как-то обрабатывать случаи, когда почта недоступна, что бы сообщение не потерялось
            return;
        }
        eventJournalService.saveConsumedEvent(alertDto.getSensorId());
        sendAlert(alertDto);
    }

    /**
     * If {@link ua.ms.consumer.impl.MailQueueConsumer#resendTimeout} minutes have passed since the last notification
     * (or the last sent time is null, that means that we haven't sent any alerts yet),
     * method will send another one
     */
    private void sendAlert(AlertDto alertDto) {
        if (canBeSent(alertDto.getSensorId())) {
            mailSender.send(alertDto);
            eventJournalService.saveSentEvent(alertDto.getSensorId());
        } else {
            eventJournalService.saveSentSkippedEvent(alertDto.getSensorId());
        }
    }
    private boolean canBeSent(long sensorId) {
        AlertEvent lastSendAlertEventBySensorId =
                eventJournalService.getLastSendEventBySensorId(sensorId, EventType.ALERT_SENT);
        return lastSendAlertEventBySensorId == null ||
                lastSendAlertEventBySensorId
                        .getDateTimeFromId()
                        .plusMinutes(parseInt(resendTimeout))
                        .isBefore(LocalDateTime.now());
    }
}