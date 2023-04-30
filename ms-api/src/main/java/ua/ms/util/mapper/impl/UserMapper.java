package ua.ms.util.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.user.User;
import ua.ms.entity.user.dto.UserDto;
import ua.ms.util.mapper.Mapper;

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
                .factoryId(user.getFactory() == null ? null : user.getFactory().getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .status(user.getStatus())
                .role(user.getRole())
                .build();
    }
}
