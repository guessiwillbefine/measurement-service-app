package ua.ms.sender.sender;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import ua.ms.consumer.impl.AlertDto;
import ua.ms.journal.ConsumerEventService;

public abstract class AbstractMailSender implements Sender<AlertDto> {
    protected final MailSender mailSender;
    protected final ConsumerEventService eventService;

    protected AbstractMailSender(ConsumerEventService eventService, MailSender mailSender) {
        this.mailSender = mailSender;
        this.eventService = eventService;
    }

    @Override
    public void send(AlertDto alertDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(alertDto.getUserMail());
        simpleMailMessage.setText(alertDto.getText());
        mailSender.send(simpleMailMessage);
    }
}
