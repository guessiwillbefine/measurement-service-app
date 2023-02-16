package ua.ms.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.mapper.UserMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.ms.TestConstants.USER_ENTITY;

@SpringBootTest
@Transactional
@ActiveProfiles("test-env")
class EntityMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Test
    void shouldReturnValidDto() {
        final UserDto userDto = userMapper.toDto(USER_ENTITY);
        assertThat(userDto.getUsername()).isEqualTo(USER_ENTITY.getUsername());
        assertThat(userDto.getId()).isEqualTo(USER_ENTITY.getId());
    }
    @Test
    void shouldReturnValidEntity() {
        final User toSave = User.builder().username("test").password("test").build();
        final UserDto dto = UserDto.builder().id(1L).username("test").build();
        final User savedUser = userRepository.save(toSave);
        assertThat(savedUser.getUsername()).isEqualTo(dto.getUsername());
        assertThat(savedUser.getId()).isEqualTo(dto.getId());
    }
    @Test
    void nonExistingUserDtoShouldReturnNull() {
        final User user = userMapper.toEntity(UserDto.builder().build());
        assertThat(user).isNull();
    }
}
