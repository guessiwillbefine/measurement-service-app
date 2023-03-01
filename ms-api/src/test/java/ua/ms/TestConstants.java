package ua.ms;

import ua.ms.entity.Factory;
import ua.ms.entity.Role;
import ua.ms.entity.Status;
import ua.ms.entity.User;
import ua.ms.entity.dto.AuthenticationCredentialsDto;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.entity.dto.UserDto;
import ua.ms.entity.dto.view.FactoryView;
import ua.ms.entity.dto.view.UserView;

import java.util.Collections;
import java.util.List;


public final class TestConstants {
    public static final AuthenticationCredentialsDto USER_CREDENTIALS = AuthenticationCredentialsDto.builder()
            .username("username")
            .password("password").build();
    public static final AuthenticationCredentialsDto INVALID_USER_CREDENTIALS = AuthenticationCredentialsDto.builder()
            .username("")
            .password("").build();
    public static final Factory FACTORY_ENTITY = Factory.builder()
            .employees(Collections.emptyList())
            .name("someFactoryName")
            .id(1L)
            .build();
    public static final FactoryDto FACTORY_DTO = FactoryDto.builder()
            .name("someFactoryName")
            .id(1L)
            .build();
    public static final User USER_ENTITY = User.builder()
            .id(1L)
            .username("username")
            .email("test@gmail.com")
            .firstName("name")
            .lastName("sname")
            .status(Status.ACTIVE)
            .role(Role.ADMIN)
            .password("password")
            .factory(FACTORY_ENTITY).build();
    public static final UserDto USER_DTO = UserDto.builder()
            .id(1L)
            .username("username")
            .email("test@gmail.com")
            .firstName("name")
            .lastName("sname")
            .status(Status.ACTIVE)
            .role(Role.ADMIN)
            .build();

    public static final UserView USER_VIEW = new UserView() {
        @Override
        public long getId() {
            return USER_ENTITY.getId();
        }

        @Override
        public String getFirstName() {
            return USER_ENTITY.getFirstName();
        }

        @Override
        public String getLastName() {
            return USER_ENTITY.getLastName();
        }

        @Override
        public String getUsername() {
            return USER_ENTITY.getUsername();
        }

        @Override
        public String getEmail() {
            return USER_ENTITY.getEmail();
        }

        @Override
        public Role getRole() {
            return USER_ENTITY.getRole();
        }

        @Override
        public Status getStatus() {
            return USER_ENTITY.getStatus();
        }

    };
    public static final FactoryView FACTORY_VIEW = new FactoryView() {
        @Override
        public long getId() {
            return 1L;
        }

        @Override
        public String getName() {
            return "someFactoryName";
        }

        @Override
        public List<UserView> getEmployees() {
            return List.of(USER_VIEW, USER_VIEW, USER_VIEW, USER_VIEW, USER_VIEW, USER_VIEW);
        }
    };
}
