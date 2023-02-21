package ua.ms.entity.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.entity.Role;
import ua.ms.entity.Status;
import ua.ms.util.ApplicationConstants.Validation;

import java.util.Objects;

@Getter
@Builder
@Jacksonized
public class UserDto {
    @Length(min = Validation.MIN_USERNAME_LENGTH,
            max = Validation.MAX_USERNAME_LENGTH,
            message = Validation.USERNAME_MSG)
    private Long id;
    @Length(min = Validation.MIN_PASSWORD_LENGTH,
            max = Validation.MAX_PASSWORD_LENGTH,
            message = Validation.PASSWORD_MSG)
    private String username;
    @Length(min = Validation.MIN_NAME_LENGTH,
            max = Validation.MAX_NAME_LENGTH,
            message = Validation.NAME_MSG)
    private String firstName;
    @Length(min = Validation.MIN_NAME_LENGTH,
            max = Validation.MAX_NAME_LENGTH,
            message = Validation.NAME_MSG)
    private String lastName;

    //email pattern : some.mail@gmail.com
    @Pattern(regexp = "^([\\w-\\.]+)@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @Nullable
    private Status status;
    @NotNull
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(email, userDto.email) &&
                status == userDto.status && role == userDto.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
