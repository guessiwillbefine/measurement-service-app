package ua.ms.services;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.ms.service.mq.impl.MailAlertService;

import java.io.Serializable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static ua.ms.TestConstants.MAIL_ALERT_DTO;

@SpringBootTest
@ActiveProfiles("test-env")
class MailAlertMessageQueueTest {
    @MockBean
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailAlertService mailAlertService;

    @Test
    void shouldPushMsgIfItIsNotNull() {
        doNothing()
                .when(rabbitTemplate)
                .convertAndSend(anyString(), anyString(), any(Serializable.class));
        mailAlertService.produce(MAIL_ALERT_DTO);
        verify(rabbitTemplate, times(1))
                .convertAndSend(anyString(), anyString(), any(Serializable.class));
    }
    @Test
    void shouldNotPushMsgIfItIsNotNull() {
        doNothing()
                .when(rabbitTemplate)
                .convertAndSend(anyString(), anyString(), any(Serializable.class));
        mailAlertService.produce(null);
        verify(rabbitTemplate, times(0))
                .convertAndSend(anyString(), anyString(), any(Serializable.class));
    }
}
