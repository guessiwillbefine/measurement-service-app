package ua.ms.service.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.socket.MeasuresSocketDto;
import ua.ms.entity.user.User;
import ua.ms.service.MachineService;
import ua.ms.service.MeasureService;
import ua.ms.service.SensorService;
import ua.ms.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class MeasureSocketServiceImpl implements MeasureSocketService {

    /**
     * Key - user, value - list of machines he is viewing.
     * New items are added when connecting via websockets
     */
    protected static final Map<User, List<Machine>> SESSION_CONTEXT = new HashMap<>();

    private final SimpMessagingTemplate messagingTemplate;

    private final MachineService machineService;

    private final SensorService sensorService;

    private final MeasureService measureService;

    private final UserService userService;

    @Override
    public void disconnect(long userId) {
        Optional<User> byId = userService.findById(userId, User.class);
        byId.ifPresent(SESSION_CONTEXT::remove);
    }

    @Override
    public int getSessionPool() {
        return SESSION_CONTEXT.size();
    }

    @Override
    public void registerNewConnection(long userId, List<Long> machineIds) {
        Optional<User> byId = userService.findById(userId, User.class);
        List<Machine> machinesByIds = machineService.getMachinesByIds(Machine.class, machineIds);
        byId.ifPresentOrElse(user -> SESSION_CONTEXT.put(user, machinesByIds),
                () -> log.error(format("Error saving %d session to context", userId)));
    }

    /**
     * Will iterate through all the connections build dto and send them the desired measurement results
     */
    @Override
    public void sendMeasureResults() {
        SESSION_CONTEXT.forEach((key, value) -> {
            String userId = String.valueOf(key.getId());
            value.stream()
                    .map(this::buildPayload)
                    .forEach(dto -> messagingTemplate.convertAndSendToUser(userId, "/queue/messages", dto));
        });
    }

    private MeasuresSocketDto buildPayload(Machine machine) {
        List<MeasuresSocketDto.SensorMessage> sensorList =
                sensorService.findAll(machine, Sensor.class).stream().map(sensor -> {
                    Measure measure = measureService.getLastMeasure(sensor.getId(), Measure.class);
                    return MeasuresSocketDto.SensorMessage.fromEntity(sensor, measure);
                }).toList();
        return new MeasuresSocketDto(new MeasuresSocketDto.MachineMessage(sensorList));
    }
}