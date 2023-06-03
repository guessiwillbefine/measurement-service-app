package ua.ms.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.machine.dto.MachineDto;
import ua.ms.entity.machine.dto.view.MachineView;
import ua.ms.service.repository.MachineRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@ActiveProfiles("test-env")
class MachineRepositoryTest {
    @Autowired
    private MachineRepository machineRepository;

    @Test
    void searchByIdShouldReturnEntity() {
        assertThat(machineRepository.findById(1L)).isPresent();
    }

    @Test
    void searchByIdShouldReturnValidType() {
        assertThat(machineRepository.findById(1L, MachineDto.class))
                .isPresent().get().isInstanceOf(MachineDto.class);
        assertThat(machineRepository.findById(1L, Machine.class))
                .isPresent().get().isInstanceOf(Machine.class);
    }

    @Test
    void pageAbleShouldReturnValidList() {
        assertThat(machineRepository.findBy(PageRequest.of(0, 1), Machine.class))
                .isInstanceOf(List.class)
                .isNotEmpty().hasSize(1);
        assertThat(machineRepository.findBy(PageRequest.of(0, 2), Machine.class))
                .isInstanceOf(List.class)
                .isNotEmpty().hasSize(2);
    }
    @Test
    void shouldReturnMachineBySensor() {
        assertThat(machineRepository.findBySensor(1L, Machine.class))
                .isPresent().get().isNotNull();
    }
    @Test
    void shouldReturnValidType() {
        assertThat(machineRepository.findBySensor(1L, Machine.class))
                .isPresent().get().isInstanceOf(Machine.class);
        assertThat(machineRepository.findBySensor(1L, MachineView.class))
                .isPresent().get().isInstanceOf(MachineView.class);
    }
}
