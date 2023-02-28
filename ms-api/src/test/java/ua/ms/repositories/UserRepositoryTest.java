package ua.ms.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserDto;
import ua.ms.entity.dto.view.UserView;
import ua.ms.service.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ua.ms.TestConstants.USER_ENTITY;

@DataJpaTest
@ActiveProfiles("test-env")
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("test returning empty optional for non-existing user")
    void returnNullForNonExistingUser() {
        assertThat(userRepository.findByUsername("bla-bla-bla")).isEmpty();
    }

    @Test
    @DisplayName("test user saving")
    void shouldRegisterNewUsers() {
        assertThat(userRepository.save(USER_ENTITY)).isNotNull();
    }

    @Test
    @DisplayName("test loading users by their usernames")
    void shouldLoadUserIfExists() {
        Optional<User> saved = Optional.of(userRepository.save(USER_ENTITY));
        assertThat(userRepository.findByUsername(USER_ENTITY.getUsername())).isEqualTo(saved);
    }

    @Test
    @DisplayName("repository should return correct class type")
    void shouldLoadUserIfExistWithCorrectType() {
        userRepository.save(USER_ENTITY);
        Optional<User> optionalUser = userRepository.findByUsername(USER_ENTITY.getUsername(), User.class);
        assertTrue(optionalUser.isPresent());
        assertThat(optionalUser.get())
                .isInstanceOf(User.class);

        Optional<UserDto> optionalUserDto = userRepository.findByUsername(USER_ENTITY.getUsername(), UserDto.class);
        assertTrue(optionalUserDto.isPresent());
        assertThat(optionalUserDto.get())
                .isInstanceOf(UserDto.class);
    }

    @Test
    @DisplayName("test deleting entities")
    void shouldDeleteValidEntity() {
        User saved = userRepository.save(USER_ENTITY);
        int amountOfDeleted = userRepository.deleteEntityById(saved.getId());
        assertThat(amountOfDeleted).isEqualTo(1L);
    }

    @Test
    @DisplayName("test pagination")
    void paginationShouldReturnValidSize() {
        int size = new Random().nextInt(1,5);
        List<User> userList = userRepository.findBy(PageRequest.of(0, size), User.class);
        assertThat(userList).hasSize(size);
    }

    @Test
    @DisplayName("test pagination return valid type")
    void paginationShouldReturnValidType() {
        int size = new Random().nextInt(1,15);
        var userList = userRepository.findBy(PageRequest.of(0, size), UserView.class);
        userList.forEach(user -> assertThat(user).isInstanceOf(UserView.class));

    }
    @Test
    void shouldReturnListWithEmployees() {
        final long factoryId = 1L;
        List<User> allByOwnerId = userRepository.findAllEmployees(factoryId, User.class);
        assertThat(allByOwnerId).isNotEmpty()
                .allMatch(x -> x.getFactory().getId() == factoryId);
    }

}
