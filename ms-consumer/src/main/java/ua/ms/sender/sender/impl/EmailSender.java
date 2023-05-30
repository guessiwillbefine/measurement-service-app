package ua.ms.sender.sender.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.ms.journal.ConsumerEventService;
import ua.ms.sender.sender.AbstractMailSender;

@Service
public class EmailSender extends AbstractMailSender {
    protected EmailSender(ConsumerEventService eventService, JavaMailSender mailSender) {
        super(eventService, mailSender);
    }
}
