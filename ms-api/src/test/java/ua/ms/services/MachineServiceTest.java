package ua.ms.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.machine.dto.MachineDto;
import ua.ms.service.MachineService;
import ua.ms.service.repository.MachineRepository;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.MACHINE_DTO;
import static ua.ms.TestConstants.MACHINE_ENTITY;

@SpringBootTest
@Transactional
@ActiveProfiles("test-env")
class MachineServiceTest {
    @MockBean
    private MachineRepository machineRepository;
    @Autowired
    private MachineService machineService;

    @Test
    @DisplayName("should return entity when searching by id")
    void shouldReturnEntityById() {
        when(machineRepository.findById(1L, MachineDto.class))
                .thenReturn(Optional.ofNullable(MACHINE_DTO));
        assertThat(machineService.findById(1L, MachineDto.class))
                .isPresent().get()
                .isEqualTo(MACHINE_DTO);
    }

    @Test
    @DisplayName("should return entity when searching by id")
    void findAllShouldReturnList() {
        when(machineRepository.findBy(any(), any(Class.class)))
                .thenReturn(List.of(MACHINE_DTO));
        assertThat(machineService.findAll(PageRequest.of(1, 1), MachineDto.class))
                .isInstanceOf(List.class);
    }

    @Test
    @DisplayName("should return entity after saving")
    void saveShouldReturnEntity() {
        when(machineRepository.save(any())).thenReturn(MACHINE_ENTITY);
        assertThat(machineService.save(MACHINE_ENTITY))
                .isInstanceOf(Machine.class)
                .isEqualTo(MACHINE_ENTITY);
    }

    @Test
    @DisplayName("test deleting entities")
    void shouldDeleteEntityById() {
        when(machineRepository.findById(1L, MachineDto.class))
                .thenReturn(Optional.of(MACHINE_DTO));
        doNothing().when(machineRepository).deleteById(anyLong());
        assertThat(machineService.delete(1L)).isEqualTo(MACHINE_DTO);

    }
    @Test
    @DisplayName("delete should throw exception if entity is not present")
    void deleteShouldThrowExceptionIfNotPresent() {
        when(machineRepository.findById(1L, MachineDto.class))
                .thenReturn(Optional.empty());
        doNothing().when(machineRepository).deleteById(anyLong());
        assertThatThrownBy(()-> machineService.delete(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("test updating entities")
    void shouldUpdateEntity() {
        when(machineRepository.findById(anyLong()))
                .thenReturn(Optional.of(MACHINE_ENTITY));
        assertThat(machineService.update(1L, MACHINE_DTO))
                .isEqualTo(MACHINE_DTO);
    }
    @Test
    @DisplayName("update should throw exception if entity is not present")
    void updateShouldThrowExceptionIfNotPresent() {
        when(machineRepository.findById(1L, MachineDto.class))
                .thenReturn(Optional.empty());
        assertThatThrownBy(()-> machineService.update(1L, MACHINE_DTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("should if Optional#empty if not present when searching by sensor id")
    void shouldReturnEmptyOptionalIfNotPresent() {
        when(machineRepository.findBySensor(1L, Machine.class))
                .thenReturn(Optional.empty());
        assertThat(machineService.findBySensorId(1L, Machine.class)).isEmpty();
    }
    @Test
    @DisplayName("should if entity if present when searching by sensor id")
    void shouldReturnEntityWhenSearching() {
        when(machineRepository.findBySensor(1L, Machine.class))
                .thenReturn(Optional.empty());
        assertThat(machineService.findBySensorId(1L, Machine.class)).isEmpty();
    }
}
