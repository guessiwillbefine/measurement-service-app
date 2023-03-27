package ua.ms.service.mq.impl.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.ms.service.mq.MessageQueueProducer;

@Log4j2
@Service
@RequiredArgsConstructor
public class MailAlertService implements MessageQueueProducer<MailAlertDto> {
    private final RabbitTemplate rabbitTemplate;
    @Value("${alert-queue.routing-key}")
    private String routingKey;
    @Value("${alert-queue.exchange}")
    private String exchange;

    @Override
    public void produce(final MailAlertDto msg) {
        if (msg != null) {
            rabbitTemplate.convertAndSend(exchange, routingKey, msg);
        } else {
            log.warn("message is null, alert won't be pushed to queue");
        }
    }
}
