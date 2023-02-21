package ua.ms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.ms.service.UserService;
import ua.ms.util.mapper.UserMapper;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.USER_DTO;
import static ua.ms.TestConstants.USER_ENTITY;

@SpringBootTest
@ActiveProfiles("test-env")
class MapperTest {
    @Autowired
    private UserMapper userMapper;
    @MockBean
    private UserService userService;
    @Test
    void testMapToEntity() {
        when(userService.findById(anyLong(), any())).thenReturn(Optional.of(USER_ENTITY));
        assertThat(userMapper.toEntity(USER_DTO)).isEqualTo(USER_ENTITY);
    }
    @Test
    void testThrowExceptionIfNotPresent() {
        assertThrows(NoSuchElementException.class, () -> userMapper.toEntity(USER_DTO));
    }
    @Test
    void testMapToDto() {
        assertThat(userMapper.toDto(USER_ENTITY)).isEqualTo(USER_DTO);
    }
}
