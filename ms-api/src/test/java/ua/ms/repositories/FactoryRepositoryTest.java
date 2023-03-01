package ua.ms.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.Factory;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.entity.dto.view.FactoryView;
import ua.ms.service.repository.FactoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test-env")
class FactoryRepositoryTest {
    @Autowired
    private FactoryRepository factoryRepository;

    @Test
    void shouldReturnPresentOptionalById() {
        Optional<Factory> byId = factoryRepository.findById(1L, Factory.class);
        assertThat(byId).isNotEmpty();
    }
    @Test
    void searchByIdShouldReturnValidType() {
        assertThat(factoryRepository.findById(1L, Factory.class)).isPresent().get()
                .isInstanceOf(Factory.class);
        assertThat(factoryRepository.findById(1L, FactoryView.class)).isPresent().get()
                .isInstanceOf(FactoryView.class);
        assertThat(factoryRepository.findById(1L, FactoryDto.class)).isPresent().get()
                .isInstanceOf(FactoryDto.class);
    }
    @Test
    void findByShouldReturnList() {
        assertThat(factoryRepository.findBy(Factory.class)).isNotEmpty().isInstanceOf(List.class);
    }
    @Test
    void findByNameShouldReturnEntity() {
        assertThat(factoryRepository.findByName("Anime factory", Factory.class))
                .isPresent().get().isInstanceOf(Factory.class);
    }
}
