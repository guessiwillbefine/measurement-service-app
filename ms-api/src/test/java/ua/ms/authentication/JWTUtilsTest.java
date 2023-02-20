package ua.ms.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.ms.configuration.security.util.JWTUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test-env")
class JWTUtilsTest {

    @Autowired
    private JWTUtils jwtUtils;

    @Test
    @DisplayName("test token generation")
    void assertValidTokenGeneratesCorrectly(){
        final String USERNAME = "testUsername";
        final String token = jwtUtils.generateToken(USERNAME);

        assertThat(token)
                .isNotNull()
                .isNotBlank();
        final String claimUsername = jwtUtils.getClaimFromToken(token);
        assertThat(claimUsername).isEqualTo(USERNAME);
    }

}
