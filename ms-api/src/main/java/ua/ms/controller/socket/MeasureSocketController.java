package ua.ms.controller.socket;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import ua.ms.service.socket.MeasureSocketService;
import java.util.List;

import static java.lang.String.format;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MeasureSocketController {
    private static final int MESSAGE_RATE = 3000;

    private final SimpMessagingTemplate messagingTemplate;

    private final MeasureSocketService measureSocketService;

    @PermitAll
    @MessageMapping("/message/{id}")
    public void connect(@DestinationVariable long id, @Payload List<Long> machineIds) {
        if (log.isDebugEnabled()) {
            log.debug(format("received payload [%s] from %s", machineIds.toString(), id));
        }
        measureSocketService.registerNewConnection(id, machineIds);
        messagingTemplate.convertAndSendToUser(String.valueOf(id), "/queue/messages", "Successfully connected!");
    }


    /**
     * Will send every {@link ua.ms.controller.socket.MeasureSocketController#MESSAGE_RATE} seconds
     * measure data to clients via socket connection
     */
    @Async
    @Scheduled(fixedRate = MESSAGE_RATE)
    public void sendValues() {
        if (log.isDebugEnabled()) {
            log.debug(format("Attempt to send values, current session context pool = %d", measureSocketService.getSessionPool()));
        }
        if (measureSocketService.getSessionPool() > 0) {
            measureSocketService.sendMeasureResults();
        }
    }
}
