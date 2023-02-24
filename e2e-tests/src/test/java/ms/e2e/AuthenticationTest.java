package ms.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.MsApiApplication;
import ua.ms.entity.dto.AuthenticationCredentialsDto;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test-env")
@Transactional
@SpringBootTest(classes = MsApiApplication.class)
@AutoConfigureMockMvc
class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DisplayName("registration process test")
    void registrationScenarioTest() throws Exception {
        final AuthenticationCredentialsDto credentialsDto = AuthenticationCredentialsDto.builder()
                .username("username").password("password").build();
        final String credentialsJson = objectMapper.writeValueAsString(credentialsDto);

        mockMvc.perform(get("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(credentialsJson))
                .andExpect(status().isForbidden());

        var response = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(credentialsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("jwt-token").exists())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        var token = objectMapper.readValue(responseBody, Map.class);

        Object tokenValue = token.get("jwt-token");
        assertThat(tokenValue).isNotNull();

        mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(credentialsJson)
                        .header("Authorization", "Bearer " + tokenValue))
                .andExpect(status().isOk());
    }
}

