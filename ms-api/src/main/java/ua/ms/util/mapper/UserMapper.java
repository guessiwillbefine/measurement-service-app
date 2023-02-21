package ua.ms.util.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {
    private final UserService userService;

    @Override
    public User toEntity(UserDto userDto) {
        Optional<User> byId = userService.findById(userDto.getId(), User.class);
        return byId.orElseThrow();
    }

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .status(user.getStatus())
                .role(user.getRole())
                .build();
    }
}
