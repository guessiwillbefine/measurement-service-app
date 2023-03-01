package ua.ms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.ms.entity.Factory;
import ua.ms.entity.User;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.FactoryService;
import ua.ms.service.UserService;
import ua.ms.util.mapper.Mapper;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.*;

@SpringBootTest
@ActiveProfiles("test-env")
class MapperTest {
    @Autowired
    private Mapper<User,UserDto> userMapper;
    @Autowired
    private Mapper<Factory, FactoryDto> factoryMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private FactoryService factoryService;
    @Test
    @DisplayName("test mapping User entity")
    void testMapToEntityUser() {
        when(userService.findById(anyLong(), any())).thenReturn(Optional.of(USER_ENTITY));
        assertThat(userMapper.toEntity(USER_DTO)).isEqualTo(USER_ENTITY);
    }
    @Test
    @DisplayName("test throwing exception in User mapper")
    void testThrowExceptionIfUserNotPresent() {
        assertThrows(NoSuchElementException.class, () -> userMapper.toEntity(USER_DTO));
    }
    @Test
    @DisplayName("test mapping User entity to dto")
    void testMapToDtoUser() {
        UserDto mappedDto = userMapper.toDto(USER_ENTITY);
        assertThat(mappedDto.getUsername()).isEqualTo(USER_ENTITY.getUsername());
        assertThat(mappedDto.getId()).isEqualTo(USER_ENTITY.getId());
        assertThat(mappedDto.getFirstName()).isEqualTo(USER_ENTITY.getFirstName());
        assertThat(mappedDto.getLastName()).isEqualTo(USER_ENTITY.getLastName());
        assertThat(mappedDto.getEmail()).isEqualTo(USER_ENTITY.getEmail());
    }


    @Test
    @DisplayName("test mapping Factory entity")
    void testMapToEntityFactory() {
        when(factoryService.findByName(anyString(), any())).thenReturn(Optional.of(FACTORY_ENTITY));
        assertThat(factoryMapper.toEntity(FACTORY_DTO)).isEqualTo(FACTORY_ENTITY);
    }
    @Test
    @DisplayName("test throwing exception in Factory mapper")
    void testThrowExceptionIfFactoryNotPresent() {
        assertThrows(NoSuchElementException.class, () -> factoryMapper.toEntity(FACTORY_DTO));
    }

    @Test
    @DisplayName("test mapping Factory entity to dto")
    void testMapToDto() {
        FactoryDto mappedDto = factoryMapper.toDto(FACTORY_ENTITY);
        assertThat(mappedDto.getId()).isEqualTo(FACTORY_ENTITY.getId());
        assertThat(mappedDto.getName()).isEqualTo(FACTORY_ENTITY.getName());
    }
}
