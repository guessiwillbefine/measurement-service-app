package ua.ms.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.Factory;
import ua.ms.entity.User;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.entity.dto.view.FactoryView;
import ua.ms.service.FactoryService;
import ua.ms.service.UserService;
import ua.ms.service.repository.FactoryRepository;
import ua.ms.util.exception.FactoryDuplicateException;
import ua.ms.util.exception.FactoryNotFoundException;
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
    void searchByIdShouldReturnEntityIfPresent() {
        when(factoryRepository.findById(anyLong(), any()))
                .thenReturn(Optional.of(FACTORY_ENTITY));
        assertThat(factoryService.findById(1L, Factory.class))
                .isEqualTo(Optional.of(FACTORY_ENTITY));
    }

    @Test
    void searchByIdShouldReturnEmptyOptionalIfNotPresent() {
        when(factoryRepository.findById(anyLong(), any()))
                .thenReturn(Optional.empty());
        assertThat(factoryService.findById(1L, Factory.class))
                .isEmpty();
    }

    @Test
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
    void searchByNameShouldReturnEntity() {
        when(factoryRepository.findByName(anyString(), any())).thenReturn(Optional.of(FACTORY_ENTITY));
        assertThat(factoryService.findByName(FACTORY_ENTITY.getName(), Factory.class))
                .isPresent().get()
                .isEqualTo(FACTORY_ENTITY);
    }

    @Test
    void shouldSaveEntityIfItNotPresentYet() {
        when(userService.findById(USER_ENTITY.getId(), User.class))
                .thenReturn(Optional.of(USER_ENTITY));
        when(factoryRepository.findByName(FACTORY_DTO.getName(), Factory.class))
                .thenReturn(Optional.empty());
        when(factoryRepository.save(any()))
                .thenReturn(FACTORY_ENTITY);

        assertThat(factoryService.save(FACTORY_DTO)).isEqualTo(FACTORY_ENTITY);
    }

    @Test
    void shouldThrowExceptionIfEntityIsPresent() {
        final Long userId = USER_ENTITY.getId();
        when(factoryRepository.findByName(anyString(), any()))
                .thenReturn(Optional.of(FACTORY_ENTITY));
        when(userService.findById(userId, User.class))
                .thenReturn(Optional.of(USER_ENTITY));
        when(factoryRepository.save(any())).thenReturn(FACTORY_ENTITY);

        assertThatThrownBy(() -> factoryService.save(FACTORY_DTO))
                .isInstanceOf(FactoryDuplicateException.class);
    }

    @Test
    void shouldReturnDeletedEntity() {
        final long id = FACTORY_ENTITY.getId();
        when(factoryRepository.findById(anyLong(), any())).thenReturn(Optional.of(FACTORY_ENTITY));
        assertThat(factoryService.deleteById(id)).isEqualTo(FACTORY_ENTITY);
    }

    @Test
    void shouldThrowExceptionIfEntityWasNotFound() {
        final long id = FACTORY_ENTITY.getId();
        when(factoryRepository.findById(anyLong(), any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> factoryService.deleteById(id))
                .isInstanceOf(FactoryNotFoundException.class);
    }

    @Test
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
    void shouldThrowExceptionWhenEntityWasNotFound() {
        when(factoryRepository.findById(anyLong(), any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> factoryService.update(1L, FACTORY_DTO))
                .isInstanceOf(FactoryNotFoundException.class);
    }

    @Test
    void findAllShouldReturnList() {
        when(factoryRepository.findBy(any())).thenReturn(List.of(FACTORY_ENTITY));
        assertThat(factoryService.findAll(Factory.class)).isNotEmpty();
    }
}
