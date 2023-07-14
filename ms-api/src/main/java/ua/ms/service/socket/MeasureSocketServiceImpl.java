package ua.ms.service.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.socket.MeasuresSocketDto;
import ua.ms.entity.user.User;
import ua.ms.service.entity.impl.MachineServiceImpl;
import ua.ms.service.entity.impl.MeasureServiceImpl;
import ua.ms.service.entity.impl.SensorServiceImpl;
import ua.ms.service.entity.impl.UserServiceImpl;
import ua.ms.service.cash.RedisService;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class MeasureSocketServiceImpl implements MeasureSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private final MachineServiceImpl machineService;

    private final SensorServiceImpl sensorService;

    private final MeasureServiceImpl measureService;

    private final UserServiceImpl userService;

    /**
     * User connection cash
     * key - user id
     * value - list of machine ids he is viewing
     * */
    private final RedisService<String, List<Long>> userSession;

    @Override
    public void disconnect(long userId) {
        Optional<User> byId = userService.findById(userId, User.class);
        byId.ifPresent(id -> userSession.removeData(String.valueOf(id)));
    }

    @Override
    public int getSessionPool() {
        return userSession.getAll().size();
    }

    @Override
    public void registerNewConnection(long userId, List<Long> machineIds) {
            Optional<User> byId = userService.findById(userId, User.class);
            byId.ifPresentOrElse(user -> userSession.saveData(String.valueOf(user.getId()), machineIds),
                () -> log.error(format("Error saving %d session to context", userId)));
    }

    /**
     * Will iterate through all the connections build dto and send them the desired measurement results
     */
    @Override
    public void sendMeasureResults() {
        userSession.getAll().forEach((key, value) -> {
            value.stream()
                    .map(this::buildPayload)
                    .forEach(dto -> messagingTemplate.convertAndSendToUser(key, "/queue/messages", dto));
        });
    }

    private MeasuresSocketDto buildPayload(Long machineId) {
        List<MeasuresSocketDto.SensorMessage> sensorList =
                sensorService.findAll(machineId, Sensor.class).stream().map(sensor -> {
                    Measure measure = measureService.getLastMeasure(sensor.getId(), Measure.class);
                    return MeasuresSocketDto.SensorMessage.fromEntity(sensor, measure);
                }).toList();
        return new MeasuresSocketDto(new MeasuresSocketDto.MachineMessage(sensorList));
    }
}