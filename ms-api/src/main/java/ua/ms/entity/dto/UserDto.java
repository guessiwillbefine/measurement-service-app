package ua.ms.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.entity.Role;
import ua.ms.entity.Status;
import ua.ms.util.ApplicationConstants;

@Getter
@Builder
@Jacksonized
public class UserDto {
    @Length(min = ApplicationConstants.Validation.MIN_USERNAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_USERNAME_LENGTH,
            message = ApplicationConstants.Validation.USERNAME_MSG)
    private Long id;
    @Length(min = ApplicationConstants.Validation.MIN_PASSWORD_LENGTH,
            max = ApplicationConstants.Validation.MAX_PASSWORD_LENGTH,
            message = ApplicationConstants.Validation.PASSWORD_MSG)
    private String username;

    private Status status;

    private Role role;
}
