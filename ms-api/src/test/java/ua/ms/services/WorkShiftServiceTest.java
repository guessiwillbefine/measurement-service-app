package ua.ms.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.work_shift.WorkShift;
import ua.ms.service.MachineService;
import ua.ms.service.WorkShiftService;
import ua.ms.service.repository.WorkShiftRepository;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.*;

@SpringBootTest
@ActiveProfiles("test-env")
@Transactional
class WorkShiftServiceTest {
    @Autowired
    private WorkShiftService workShiftService;
    @MockBean
    private WorkShiftRepository workShiftRepository;
    @MockBean
    private MachineService machineService;

    @Test
    void getAllShouldReturnList() {
        when(workShiftRepository.findBy(PageRequest.of(0, 5), WorkShift.class))
                .thenReturn(new ArrayList<>());
        assertThat(workShiftService.getAll(PageRequest.of(0, 5), WorkShift.class))
                .isNotNull();
    }

    @Test
    void getAllByWorkerShouldReturnList() {
        when(workShiftRepository.findByWorkerId(1L, PageRequest.of(0, 5), WorkShift.class))
                .thenReturn(new ArrayList<>());
        assertThat(workShiftService.getAllByWorker(1L, PageRequest.of(0, 5), WorkShift.class))
                .isNotNull();
    }

//    @Test
//    void saveShouldReturnEntity() {
//        when(machineService.findById(MACHINE_ENTITY.getId(), Machine.class))
//                .thenReturn(Optional.of(MACHINE_ENTITY));
//        when(workShiftRepository.save(WORK_SHIFT_ENTITY))
//                .thenReturn(WORK_SHIFT_ENTITY);
//
//        assertThat(workShiftService.save(WORK_SHIFT_ENTITY)).isEqualTo(WORK_SHIFT_ENTITY);
//    }

//    @Test
//    void saveShouldThrowEntityNotFoundException() {
//        when(machineService.findById(MACHINE_ENTITY.getId(), Machine.class))
//                .thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> workShiftService.save(WORK_SHIFT_ENTITY))
//                .isInstanceOf(EntityNotFoundException.class);
//    }

//    @Test
//    void saveShouldThrowIllegalStateException() {
//        when(machineService.findById(MACHINE_ENTITY.getId(), Machine.class))
//                .thenReturn(Optional.of(ACTIVE_MACHINE_ENTITY));
//
//        assertThatThrownBy(() -> workShiftService.save(WORK_SHIFT_ENTITY))
//                .isInstanceOf(IllegalStateException.class);
//    }
//
//    @Test
//    void updateShouldReturnEntity() {
//        when(workShiftRepository.findById(WORK_SHIFT_ENTITY.getId()))
//                .thenReturn(Optional.of(WORK_SHIFT_ENTITY));
//        when(machineService.findById(MACHINE_ENTITY.getId(), Machine.class))
//                .thenReturn(Optional.of(MACHINE_ENTITY));
//        when(workShiftRepository.save(WORK_SHIFT_ENTITY))
//                .thenReturn(WORK_SHIFT_ENTITY);
//
//        assertThat(workShiftService.save(WORK_SHIFT_ENTITY)).isEqualTo(WORK_SHIFT_ENTITY);
//    }
//
//    @Test
//    void updateShouldThrowWorkShiftNotFoundException() {
//        when(workShiftRepository.findById(WORK_SHIFT_ENTITY.getId()))
//                .thenReturn(Optional.empty());
//
//        assertThatThrownBy(()->workShiftService.save(WORK_SHIFT_ENTITY))
//                .isInstanceOf(EntityNotFoundException.class);
//    }
//
//    @Test
//    void updateShouldThrowMachineNotFoundException() {
//        when(workShiftRepository.findById(WORK_SHIFT_ENTITY.getId()))
//                .thenReturn(Optional.of(WORK_SHIFT_ENTITY));
//        when(machineService.findById(MACHINE_ENTITY.getId(), Machine.class))
//                .thenReturn(Optional.empty());
//
//        assertThatThrownBy(()->workShiftService.save(WORK_SHIFT_ENTITY))
//                .isInstanceOf(EntityNotFoundException.class);
//    }

//    @Test
//    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
//    void deleteShouldReturnEntity() {
//        when(workShiftRepository.findByIdAndWorkerId(1L, 1L, WorkShift.class))
//                .thenReturn(Optional.of(WORK_SHIFT_ENTITY));
//        doNothing().when(workShiftRepository).delete(WORK_SHIFT_ENTITY);
//
//        assertThat(workShiftService.delete(1L)).isEqualTo(WORK_SHIFT_ENTITY);
//    }
}
