package ua.ms.controller.socket;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import ua.ms.service.socket.MeasureSocketService;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MeasureSocketController {
    private static final int MESSAGE_RATE = 10000;

    private final SimpMessagingTemplate messagingTemplate;

    private final MeasureSocketService measureSocketService;
    @PermitAll
    @MessageMapping("/message/{id}")
    public void connect(@DestinationVariable String id, @Payload List<Long> machineIds) {
        measureSocketService.registerNewConnection(Long.parseLong(id), machineIds);
        messagingTemplate.convertAndSendToUser(id, "/queue/messages", "connected " + id);
    }


    /**
     * Будем каждые {@link ua.ms.controller.socket.MeasureSocketController#MESSAGE_RATE} секунд отправлять результаты измерений
     */
    @Scheduled(fixedRate = MESSAGE_RATE)
    public void sendValues() {
        if (log.isDebugEnabled()) {
            log.debug("Attempt to send values, current session context pool = " + measureSocketService.getSessionPool());
        }
        if (measureSocketService.getSessionPool() > 0) {
            measureSocketService.sendMeasureResults();
        }
    }
}
