package ua.ms.util.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.UserService;

@Component
@RequiredArgsConstructor
public class UserMapper implements EntityMapper<User, UserDto> {
    private final UserService userService;

    @Override
    public User toEntity(UserDto userDto) {
        return userService
                .loadByUsername(userDto.getUsername())
                .orElse(null);
    }

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername()).build();
    }
}
