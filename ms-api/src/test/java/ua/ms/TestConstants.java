package ua.ms;


import ua.ms.entity.Role;
import ua.ms.entity.Status;
import ua.ms.entity.User;
import ua.ms.entity.dto.AuthenticationCredentialsDto;
import ua.ms.entity.dto.UserDto;


public final class TestConstants {
    public static final AuthenticationCredentialsDto USER_CREDENTIALS = AuthenticationCredentialsDto.builder()
            .username("username")
            .password("password").build();
    public static final AuthenticationCredentialsDto INVALID_USER_CREDENTIALS = AuthenticationCredentialsDto.builder()
            .username("")
            .password("").build();
    public static final User USER_ENTITY = User.builder()
            .id(1L)
            .username("username")
            .email("test@gmail.com")
            .firstName("name")
            .lastName("sname")
            .status(Status.ACTIVE)
            .role(Role.ADMIN)
            .password("password").build();
    public static final UserDto USER_DTO = UserDto.builder()
            .id(1L)
            .username("username")
            .email("test@gmail.com")
            .firstName("name")
            .lastName("sname")
            .status(Status.ACTIVE)
            .role(Role.ADMIN)
            .build();
}
