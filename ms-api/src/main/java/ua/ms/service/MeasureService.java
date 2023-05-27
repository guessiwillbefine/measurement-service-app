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
import ua.ms.util.exception.EntityNotFoundException;

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
        long id = measure.getSensor().getId();
        Optional<Sensor> sensor = sensorService.findOne(id, Sensor.class);
        if (sensor.isEmpty()) {
            log.error(format("Error while attempting to find sensor with id[%d]", id));
            throw new EntityNotFoundException(format("Entity[%s] with id[%d] wasn't found", Sensor.class.getSimpleName(), id));
        }
        MailAlertDto mailAlertDto = buildDto(measure, sensor.get());
        mailAlertService.push(mailAlertDto);
        return measureRepository.save(measure);
    }

    private MailAlertDto buildDto(Measure measure, Sensor sensor) {
        Machine machine = sensor.getMachine();
        WorkShift workerByMachine = workShiftService.getWorkerByMachine(sensor.getMachine());
        return new MailAlertDto(sensor.getId(), workerByMachine.getWorker().getEmail(),
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
