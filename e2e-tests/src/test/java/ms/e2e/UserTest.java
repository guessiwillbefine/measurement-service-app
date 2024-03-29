package ms.e2e;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.TestContainersIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.ms.MsApiApplication;
import ua.ms.entity.user.dto.AuthenticationCredentialsDto;
import ua.ms.entity.user.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ms.util.ApplicationConstants.Security.*;

/** user scenario tests - modifying, deleting entities. **/

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(classes = MsApiApplication.class)
class UserTest extends TestContainersIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private final AuthenticationCredentialsDto adminCredentials = AuthenticationCredentialsDto.builder()
            .username("admin").password("admin").build();

    @BeforeAll
    public static void startContainer() {
        startContainers();
    }

    @Test
    @DisplayName("test deleting users")
    void testDeletingUser() throws Exception {
        final String credentialsJson = objectMapper.writeValueAsString(adminCredentials);

        var response = mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(credentialsJson))
                .andExpect(status().isOk()).andReturn();

        final String token = getToken(response.getResponse().getContentAsString());

        var allUsersResponse = mockMvc.perform(get("/users/all")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();

        String responseString = allUsersResponse.getResponse().getContentAsString();
        Object[] allUsersList = objectMapper.readValue(responseString, Object[].class);
        assertThat(allUsersList).isNotNull();

        var userResponse = mockMvc.perform(get("/users/2")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();

        var user = objectMapper.readValue(userResponse.getResponse().getContentAsString(), UserDto.class);

        var deletedUserResponse = mockMvc.perform(delete("/users/2")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();

        String deletedUserJson = deletedUserResponse.getResponse().getContentAsString();
        var deletedUser = objectMapper.readValue(deletedUserJson, UserDto.class);

        assertThat(user.getId()).isEqualTo(deletedUser.getId());

        var allUsersResponseAfterDelete = mockMvc.perform(get("/users/all")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();

        String responseStringAfterDelete = allUsersResponseAfterDelete.getResponse().getContentAsString();
        Object[] allUsersListAfterDelete = objectMapper.readValue(responseStringAfterDelete, Object[].class);

        assertThat(allUsersList.length - allUsersListAfterDelete.length).isSameAs(1);
    }

    @Test
    @DisplayName("test updating users")
    void testUpdatingUser() throws Exception {
        final String credentialsJson = objectMapper.writeValueAsString(adminCredentials);
        final String newName = "newName";
        var response = mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(credentialsJson))
                .andExpect(status().isOk()).andReturn();
        final String token = getToken(response.getResponse().getContentAsString());

        var userResponse = mockMvc.perform(get("/users/1")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();
        String userBody = userResponse.getResponse().getContentAsString();
        var userData = objectMapper.readValue(userBody, Map.class);

        var updatedUser = new HashMap<>();
        updatedUser.putAll(userData);
        updatedUser.put("firstName", newName);

        String updatedUserJson = objectMapper.writeValueAsString(updatedUser);
        var responseAfterUpdate = mockMvc.perform(patch("/users/1")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andReturn();

        String updatedUserBody = responseAfterUpdate.getResponse().getContentAsString();
        var updatedUserData = objectMapper.readValue(updatedUserBody, Map.class);

        assertThat(updatedUserData.get("firstName"))
                .isNotNull()
                .isNotEqualTo(userData.get("firstName"))
                .isEqualTo(newName);

    }

    private String getToken(String contentAsString) throws JsonProcessingException {
        var body = objectMapper.readValue(contentAsString, Map.class);
        return body.get(JWT_TOKEN_RESPONSE_KEY).toString();
    }
}
