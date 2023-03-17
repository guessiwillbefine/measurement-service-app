package ua.ms.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.service.repository.SensorRepository;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test-env")
class SensorRepositoryTest {
    @Autowired
    private SensorRepository sensorRepository;

    @Test
    void paginationShouldReturnValidSize(){
        int size = new Random().nextInt(1, 5);
        List<Sensor> sensors = sensorRepository.findBy(PageRequest.of(0, size), Sensor.class);
        assertThat(sensors).hasSize(size);
    }

    @Test
    void paginationShouldReturnValidType(){
        int size = new Random().nextInt(1, 5);
        List<Sensor> sensors1 = sensorRepository.findBy(PageRequest.of(0, size), Sensor.class);
        List<SensorDto> sensors2 = sensorRepository.findBy(PageRequest.of(0, size), SensorDto.class);

        sensors1.forEach(sensor -> assertThat(sensor).isInstanceOf(Sensor.class));
        sensors2.forEach(sensor -> assertThat(sensor).isInstanceOf(SensorDto.class));
    }

    @Test
    void findByIdShouldReturnEntity(){
        assertThat(sensorRepository.findById(1, Sensor.class))
                .isNotEmpty().get()
                .isInstanceOf(Sensor.class);
        assertThat(sensorRepository.findById(1, SensorDto.class))
                .isNotEmpty().get()
                .isInstanceOf(SensorDto.class);
    }

    @Test
    void findByNameShouldReturnEntity(){
        assertThat(sensorRepository.findByName("TestSensor1", Sensor.class))
                .isNotEmpty().get()
                .isInstanceOf(Sensor.class);
        assertThat(sensorRepository.findByName("TestSensor1", SensorDto.class))
                .isNotEmpty().get()
                .isInstanceOf(SensorDto.class);
    }
}
