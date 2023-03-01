package ua.ms.entity.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.entity.Role;
import ua.ms.entity.Status;
import ua.ms.util.ApplicationConstants.Validation;

@Getter
@Builder
@Jacksonized
@Tag(name = "User DTO")
public class UserDto {
    @Nullable
    private Long id;
    @Length(min = Validation.MIN_USERNAME_LENGTH,
            max = Validation.MAX_USERNAME_LENGTH,
            message = Validation.USERNAME_MSG)
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

    @Email(regexp = Validation.EMAIL_REGEXP,
            message = Validation.EMAIL_MSG)

    private String email;
    @Nullable
    private Status status;
    @NotNull
    private Role role;
}
