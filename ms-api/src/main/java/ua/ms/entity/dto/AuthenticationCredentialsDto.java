package ua.ms.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class AuthenticationCredentialsDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
