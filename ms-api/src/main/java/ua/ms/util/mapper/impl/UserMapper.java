package ua.ms.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.Factory;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.UserService;
import ua.ms.util.mapper.Mapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public User toEntity(UserDto userDto) {
       return User.builder()
               .id(userDto.getId())
               .username(userDto.getUsername())
               .factory(Factory.builder().id(userDto.getFactoryId())
                       .build())
               .status(userDto.getStatus())
               .role(userDto.getRole())
               .email(userDto.getEmail())
               .firstName(userDto.getFirstName())
               .lastName(userDto.getLastName())
               .build();
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
