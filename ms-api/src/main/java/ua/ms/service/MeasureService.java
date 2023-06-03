package ua.ms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.measure.AbstractMeasureIdentifiable;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.work_shift.WorkShift;
import ua.ms.service.mq.impl.mail.MailAlertDto;
import ua.ms.service.mq.impl.mail.MailAlertService;
import ua.ms.service.repository.MeasureRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class MeasureService {

    private final MeasureRepository measureRepository;
    private final MailAlertService mailAlertService;
    private final SensorService sensorService;
    private final WorkShiftService workShiftService;

    @Transactional(readOnly = true)
    public <T extends AbstractMeasureIdentifiable> List<T> findBySensorId(long id, Class<T> type) {
        return measureRepository.findBySensorIdOrderByCreatedAtDesc(id, type);
    }

    @Transactional
    public Measure create(Measure measure) {
        measure.setCreatedAt(LocalDateTime.now());
        Measure saved = measureRepository.save(measure);
        Optional<Sensor> sensor = sensorService.findOne(saved.getSensor().getId(), Sensor.class);
        if (sensor.isPresent()) {
            saved.setSensor(sensor.get());
            if (saved.isCriticalSafe()) {
                /* если не удалось собрать дто, значит workshift не найден и отправлять нам некуда */
                MailAlertDto mailAlertDto = buildDto(measure, measure.getSensor());
                if (mailAlertDto != null) {
                    mailAlertService.push(mailAlertDto);
                }
            }
            return saved;
        }
        log.error("Measure value was saved successfully, but sensor wasn't found by same id");
        throw new IllegalStateException("");
    }

    private MailAlertDto buildDto(Measure measure, Sensor sensor) {
        Machine machine = sensor.getMachine();
        Optional<WorkShift> workerByMachine = workShiftService.getWorkerByMachine(sensor.getMachine());
        if (workerByMachine.isEmpty()) {
            log.warn(format("Workshift for machine[%s] was not found, there is no user to receive alert",
                    sensor.getMachine().getId()));
            return null;
        }
        return new MailAlertDto(sensor.getId(), workerByMachine.get().getWorker().getEmail(),
                sensor.getName(), machine.getModel(), machine.getName(),
                measure.getValue(), sensor.getCriticalValue());
    }

    @Transactional
    public List<Measure> delete(long id) {
        return measureRepository.deleteBySensorId(id);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractMeasureIdentifiable> T getLastMeasure(long sensorId, Class<T> type) {
        return measureRepository.getLastMeasure(sensorId, type);
    }
}
