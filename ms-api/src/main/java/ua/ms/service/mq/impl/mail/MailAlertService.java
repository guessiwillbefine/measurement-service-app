package ua.ms.service.mq.impl.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import ua.ms.service.mq.MessageQueueProducer;
import ua.ms.util.journal.EventServiceImpl;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class MailAlertService implements MessageQueueProducer<MailAlertDto> {

    @Value("${alert-queue.routing-key}")
    private String routingKey;

    @Value("${alert-queue.exchange}")
    private String exchange;

    private final EventServiceImpl eventService;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public void push(final MailAlertDto msg) {
        if (msg != null) {
            String mailJson;
            try {
                mailJson = objectMapper.writeValueAsString(msg.asMessage());
            } catch (JsonProcessingException e) {
                log.error(format("Error while serializing [%s]", msg.getClass()));
                throw new IllegalStateException(e);
            }
            rabbitTemplate.convertAndSend(exchange, routingKey, new GenericMessage<>(mailJson));
            eventService.saveProducingAlertEvent(msg.getSensorId());
        } else {
            log.warn("message is null, alert won't be pushed to queue");
        }
    }

}
