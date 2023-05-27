package ua.ms.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.measure.dto.MeasureDto;
import ua.ms.entity.measure.dto.view.MeasureView;
import ua.ms.service.MeasureService;
import ua.ms.service.repository.MeasureRepository;
import ua.ms.util.journal.EventServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test-env")
class MeasureServiceTest {
    @Autowired
    private MeasureService measureService;

    @MockBean
    private MeasureRepository measureRepository;

    @MockBean
    private EventServiceImpl eventService;

    @Test
    void findBySensorIdShouldReturnList(){
        when(measureRepository.findBySensorIdOrderByCreatedAtDesc(1L, Measure.class))
                .thenReturn(new ArrayList<>());
        assertThat(measureService.findBySensorId(1L, Measure.class)).isNotNull();
    }

    @Test
    void findBySensorIdShouldReturnValidaType() {
        when(measureRepository.findBySensorIdOrderByCreatedAtDesc(1L, Measure.class))
                .thenReturn(List.of(MEASURE_ENTITY));
        when(measureRepository.findBySensorIdOrderByCreatedAtDesc(1L, MeasureDto.class))
                .thenReturn(List.of(MEASURE_DTO));
        when(measureRepository.findBySensorIdOrderByCreatedAtDesc(1L, MeasureView.class))
                .thenReturn(List.of(MEASURE_VIEW));

        measureService.findBySensorId(1L, Measure.class)
                .forEach(measure -> assertThat(measure).isInstanceOf(Measure.class));
        measureService.findBySensorId(1L, MeasureDto.class)
                .forEach(measure -> assertThat(measure).isInstanceOf(MeasureDto.class));
        measureService.findBySensorId(1L, MeasureView.class)
                .forEach(measure -> assertThat(measure).isInstanceOf(MeasureView.class));
    }

    @Test
    void createShouldReturnEntity(){
        when(measureRepository.save(MEASURE_ENTITY)).thenReturn(MEASURE_ENTITY);

        assertThat(measureService.create(MEASURE_ENTITY)).isEqualTo(MEASURE_ENTITY);
    }

    @Test
    void shouldReturnLastMeasure() {
        when(measureRepository.getLastMeasure(anyLong(), any(Class.class))).thenReturn(MEASURE_ENTITY);
        assertThat(measureService.getLastMeasure(1L, Measure.class)).isNotNull();
    }
    @Test
    void shouldReturnLastWithCorrectType() {
        when(measureRepository.getLastMeasure(1L, MeasureDto.class)).thenReturn(MEASURE_DTO);
        assertThat(measureService.getLastMeasure(1L, MeasureDto.class)).isInstanceOf(MeasureDto.class);
    }
}
