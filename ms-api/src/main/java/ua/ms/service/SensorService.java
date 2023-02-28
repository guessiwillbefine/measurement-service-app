package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.Sensor;
import ua.ms.service.repository.SensorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;

    @Transactional(readOnly = true)
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Sensor> findOne(int id) {
        return sensorRepository.findById(id);
    }
}
