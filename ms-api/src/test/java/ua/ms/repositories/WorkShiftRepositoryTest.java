package ua.ms.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.work_shift.WorkShift;
import ua.ms.entity.work_shift.dto.view.WorkShiftView;
import ua.ms.service.repository.WorkShiftRepository;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test-env")
@Transactional
class WorkShiftRepositoryTest {
    @Autowired
    private WorkShiftRepository workShiftRepository;

    @Test
    void findByPaginationShouldReturnValidSize() {
        int size = new Random().nextInt(1, 5);
        List<WorkShift> workShifts = workShiftRepository.findBy(PageRequest.of(0, size), WorkShift.class);
        assertThat(workShifts).hasSize(size);
    }

    @Test
    void findByIdPaginationShouldReturnValidSize() {
        int size = new Random().nextInt(1, 5);
        System.out.println(size);
        List<WorkShift> workShifts = workShiftRepository.findByWorkerId(1L, PageRequest.of(0, size), WorkShift.class);
        assertThat(workShifts).hasSize(size);
    }

    @Test
    void findByPaginationShouldReturnValidType() {
        int size = new Random().nextInt(1, 5);
        List<WorkShiftView> workShifts1 = workShiftRepository.findBy(PageRequest.of(0, size), WorkShiftView.class);
        List<WorkShift> workShifts2 = workShiftRepository.findBy(PageRequest.of(0, size), WorkShift.class);

        workShifts1.forEach(workShiftView -> assertThat(workShiftView).isInstanceOf(WorkShiftView.class));
        workShifts2.forEach(workShift -> assertThat(workShift).isInstanceOf(WorkShift.class));
    }

    @Test
    void findByIdPaginationShouldReturnValidType() {
        int size = new Random().nextInt(1, 5);
        List<WorkShiftView> workShifts1 = workShiftRepository.findByWorkerId(1L, PageRequest.of(0, size), WorkShiftView.class);
        List<WorkShift> workShifts2 = workShiftRepository.findByWorkerId(1L, PageRequest.of(0, size), WorkShift.class);

        workShifts1.forEach(workShiftView -> assertThat(workShiftView).isInstanceOf(WorkShiftView.class));
        workShifts2.forEach(workShift -> assertThat(workShift).isInstanceOf(WorkShift.class));
    }

    @Test
    void findByIdAndWorkerIdShouldReturnEntity() {
        assertThat(workShiftRepository.findByIdAndWorkerId(1L, 1L, WorkShift.class))
                .isNotEmpty().get()
                .isInstanceOf(WorkShift.class);
        assertThat(workShiftRepository.findByIdAndWorkerId(2L, 1L, WorkShift.class))
                .isNotEmpty().get()
                .isInstanceOf(WorkShift.class);
    }
}
