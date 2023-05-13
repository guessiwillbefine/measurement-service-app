package ua.ms.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.entity.sensor.dto.view.SensorView;
import ua.ms.service.SensorService;
import ua.ms.service.repository.SensorRepository;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test-env")
class SensorServiceTest {
    @Autowired
    private SensorService sensorService;
    @MockBean
    private SensorRepository sensorRepository;

    @Test
    void findAllShouldReturnList() {
        when(sensorRepository.findBy(PageRequest.of(0, 5), Sensor.class))
                .thenReturn(new ArrayList<>());
        assertThat(sensorService.findAll(PageRequest.of(0, 5), Sensor.class))
                .isNotNull();
    }

    @Test
    void findOneShouldReturnEntity() {
        when(sensorRepository.findById(1L, Sensor.class))
                .thenReturn(Optional.of(SENSOR_ENTITY));

        assertThat(sensorService.findOne(1L, Sensor.class))
                .isEqualTo(Optional.of(SENSOR_ENTITY));
    }

    @Test
    void findOneShouldReturnEmptyOptionalIfNotFound() {
        when(sensorRepository.findById(1L, Sensor.class))
                .thenReturn(Optional.empty());

        assertThat(sensorService.findOne(1L, Sensor.class))
                .isEmpty();
    }

    @Test
    void findOneShouldReturnValidType() {
        when(sensorRepository.findById(1L, Sensor.class))
                .thenReturn(Optional.of(SENSOR_ENTITY));
        when(sensorRepository.findById(1L, SensorDto.class))
                .thenReturn(Optional.of(SENSOR_DTO));
        when(sensorRepository.findById(1L, SensorView.class))
                .thenReturn(Optional.of(SENSOR_VIEW));

        assertThat(sensorService.findOne(1L, Sensor.class))
                .isEqualTo(Optional.of(SENSOR_ENTITY));
        assertThat(sensorService.findOne(1L, SensorDto.class))
                .isEqualTo(Optional.of(SENSOR_DTO));
        assertThat(sensorService.findOne(1L, SensorView.class))
                .isEqualTo(Optional.of(SENSOR_VIEW));
    }

    @Test
    void updateShouldReturnEntity() {
        when(sensorRepository.findById(SENSOR_ENTITY.getId()))
                .thenReturn(Optional.of(SENSOR_ENTITY));
        when(sensorRepository.save(SENSOR_ENTITY))
                .thenReturn(SENSOR_ENTITY);

        assertThat(sensorService.update(1L, SENSOR_DTO))
                .isEqualTo(SENSOR_ENTITY);
    }

    @Test
    void updateShouldThrowExceptionIfSensorIsNotFound() {
        when(sensorRepository.findById(SENSOR_ENTITY.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorService.update(1L, SENSOR_DTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteShouldReturnEntity() {
        when(sensorRepository.findById(SENSOR_ENTITY.getId()))
                .thenReturn(Optional.of(SENSOR_ENTITY));
        doNothing().when(sensorRepository).delete(SENSOR_ENTITY);

        assertThat(sensorService.delete(1L))
                .isEqualTo(SENSOR_ENTITY);
    }

    @Test
    void deleteShouldThrowExceptionIfSensorIsNotFound() {
        when(sensorRepository.findById(SENSOR_ENTITY.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorService.delete(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void createShouldReturnEntity() {
        when(sensorRepository.findByName(SENSOR_ENTITY.getName(), Sensor.class))
                .thenReturn(Optional.empty());
        when(sensorRepository.save(SENSOR_ENTITY))
                .thenReturn(SENSOR_ENTITY);

        assertThat(sensorService.create(SENSOR_ENTITY)).isEqualTo(SENSOR_ENTITY);
    }

}
