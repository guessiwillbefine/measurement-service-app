package ua.ms.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.measure.dto.MeasureDto;
import ua.ms.entity.measure.dto.view.MeasureView;
import ua.ms.service.repository.MeasureRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test-env")
class MeasureRepositoryTest {
    @Autowired
    private MeasureRepository measureRepository;

    @Test
    void findBySensorIdShouldReturnValidType() {
        List<Measure> measures1 = measureRepository.findBySensorIdOrderByCreatedAtDesc(1L, Measure.class);
        List<MeasureDto> measures2 = measureRepository.findBySensorIdOrderByCreatedAtDesc(1L, MeasureDto.class);
        List<MeasureView> measures3 = measureRepository.findBySensorIdOrderByCreatedAtDesc(1L, MeasureView.class);

        measures1.forEach(measure -> assertThat(measure).isInstanceOf(Measure.class));
        measures2.forEach(measure -> assertThat(measure).isInstanceOf(MeasureDto.class));
        measures3.forEach(measure -> assertThat(measure).isInstanceOf(MeasureView.class));
    }
}
