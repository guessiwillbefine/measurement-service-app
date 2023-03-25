package ua.ms.entity.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import ua.ms.util.ApplicationConstants;

@Getter
@Builder
@Jacksonized
public class AuthenticationCredentialsDto {
    @Length(min = ApplicationConstants.Validation.MIN_USERNAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_USERNAME_LENGTH,
            message = ApplicationConstants.Validation.USERNAME_MSG)
    private String username;
    @Length(min = ApplicationConstants.Validation.MIN_USERNAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_USERNAME_LENGTH,
            message = ApplicationConstants.Validation.PASSWORD_MSG)
    private String password;
}
