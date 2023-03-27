package ua.ms.consumer.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ua.ms.consumer.MessageQueueConsumer;


//заглушка, до тех пор пока producer не реализован, что бы очередь очищалась
@Log4j2
@Component
public class MailQueueConsumer implements MessageQueueConsumer<Object> {
    private static final String ALERT_QUEUE = "notification-queue";
    @Override
    @RabbitListener(queues = {ALERT_QUEUE})
    public void consume(Object o) {
        log.info(o + " consumed");
    }
}