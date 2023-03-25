package ua.ms.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.factory.dto.FactoryDto;
import ua.ms.entity.factory.dto.view.FactoryView;
import ua.ms.entity.user.User;
import ua.ms.service.FactoryService;
import ua.ms.service.UserService;
import ua.ms.service.repository.FactoryRepository;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test-env")
class FactoryServiceTest {
    @Autowired
    private FactoryService factoryService;
    @MockBean
    private FactoryRepository factoryRepository;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("search by id test")
    void searchByIdShouldReturnEntityIfPresent() {
        when(factoryRepository.findById(anyLong(), any()))
                .thenReturn(Optional.of(FACTORY_ENTITY));
        assertThat(factoryService.findById(1L, Factory.class))
                .isEqualTo(Optional.of(FACTORY_ENTITY));
    }

    @Test
    @DisplayName("test returning optional empty if entity isn't present")
    void searchByIdShouldReturnEmptyOptionalIfNotPresent() {
        when(factoryRepository.findById(anyLong(), any()))
                .thenReturn(Optional.empty());
        assertThat(factoryService.findById(1L, Factory.class))
                .isEmpty();
    }

    @Test
    @DisplayName("test returning valid type")
    void searchByIdShouldReturnValidType() {
        when(factoryRepository.findById(1L, Factory.class))
                .thenReturn(Optional.of(FACTORY_ENTITY));
        when(factoryRepository.findById(1L, FactoryDto.class))
                .thenReturn(Optional.of(FACTORY_DTO));
        when(factoryRepository.findById(1L, FactoryView.class))
                .thenReturn(Optional.of(FACTORY_VIEW));

        Optional<Factory> factoryEntity = factoryService.findById(1L, Factory.class);
        assertThat(factoryEntity).isPresent().get()
                .isInstanceOf(Factory.class);

        Optional<FactoryDto> factoryDto = factoryService.findById(1L, FactoryDto.class);
        assertThat(factoryDto).isPresent().get()
                .isInstanceOf(FactoryDto.class);

        Optional<FactoryView> factoryView = factoryService.findById(1L, FactoryView.class);
        assertThat(factoryView).isPresent().get()
                .isInstanceOf(FactoryView.class);
    }

    @Test
    @DisplayName("test searching by id")
    void searchByNameShouldReturnEntity() {
        when(factoryRepository.findByName(anyString(), any())).thenReturn(Optional.of(FACTORY_ENTITY));
        assertThat(factoryService.findByName(FACTORY_ENTITY.getName(), Factory.class))
                .isPresent().get()
                .isEqualTo(FACTORY_ENTITY);
    }

    @Test
    @DisplayName("test saving entity")
    void shouldSaveEntityIfItNotPresentYet() {
        when(userService.findById(USER_ENTITY.getId(), User.class))
                .thenReturn(Optional.of(USER_ENTITY));
        when(factoryRepository.findByName(FACTORY_DTO.getName(), Factory.class))
                .thenReturn(Optional.empty());
        when(factoryRepository.save(any()))
                .thenReturn(FACTORY_ENTITY);

        assertThat(factoryService.save(FACTORY_ENTITY)).isEqualTo(FACTORY_ENTITY);
    }

    @Test
    @DisplayName("test throwing exception if duplicating entity")
    void shouldThrowExceptionIfEntityIsPresent() {
        when(factoryRepository.save(FACTORY_ENTITY)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> factoryService.save(FACTORY_ENTITY))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(EntityDuplicateException.class);
    }

    @Test
    @DisplayName("test returning deleted entity")
    void shouldReturnDeletedEntity() {
        final long id = FACTORY_ENTITY.getId();
        when(factoryRepository.findById(anyLong(), any())).thenReturn(Optional.of(FACTORY_ENTITY));
        assertThat(factoryService.deleteById(id)).isEqualTo(FACTORY_ENTITY);
    }

    @Test
    @DisplayName("test throwing exception if entity was not found in delete method")
    void shouldThrowExceptionIfEntityWasNotFound() {
        final long id = FACTORY_ENTITY.getId();
        when(factoryRepository.findById(anyLong(), any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> factoryService.deleteById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("test updating entities")
    void shouldReturnUpdatedEntity() {
        final long id = FACTORY_DTO.getId();
        final FactoryDto toUpdate = FACTORY_DTO;
        toUpdate.setName("newName");
        final Factory updated = Factory.builder()
                .name(toUpdate.getName()).id(toUpdate.getId()).build();


        when(factoryRepository.findById(anyLong(), any())).thenReturn(Optional.of(FACTORY_ENTITY));
        when(factoryRepository.save(any())).thenReturn(updated);

        Factory testUpdated = factoryService.update(id, toUpdate);
        assertThat(testUpdated.getName()).isEqualTo(toUpdate.getName());
    }

    @Test
    @DisplayName("test throwing exception if factory wasn't found")
    void shouldThrowExceptionWhenEntityWasNotFound() {
        when(factoryRepository.findById(anyLong(), any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> factoryService.update(1L, FACTORY_DTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("find all factories test")
    void findAllShouldReturnList() {
        when(factoryRepository.findBy(any())).thenReturn(List.of(FACTORY_ENTITY));
        assertThat(factoryService.findAll(Factory.class)).isNotEmpty();
    }
}
