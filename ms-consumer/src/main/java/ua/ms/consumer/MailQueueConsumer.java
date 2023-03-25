package ua.ms.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MailQueueConsumer implements MessageQueueConsumer {
    @Override
    @RabbitListener(queues = {"notification-queue"})
    public void consume(Object o) {
        System.out.println(o + " consumed");
    }
}