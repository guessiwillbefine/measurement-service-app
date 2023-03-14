package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.Measure;
import ua.ms.service.repository.MeasureRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasureService {
    private final MeasureRepository measureRepository;

    @Transactional(readOnly = true)
    public <T> List<T> findBySensorId(long id, Class<T> type) {
        return measureRepository.findBySensorIdOrderByCreatedAtDesc(id, type);
    }

    @Transactional
    public Measure create(Measure measure) {
        measure.setCreatedAt(LocalDateTime.now());

        return measureRepository.save(measure);
    }

    @Transactional
    public List<Measure> delete(long id) {
        return measureRepository.deleteBySensorId(id);
    }
}
