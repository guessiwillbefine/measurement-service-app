package ms.e2e;

import ms.TestContainersIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ua.ms.MsApiApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = MsApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class E2eTestsApplicationTests extends TestContainersIntegrationTest {

    @LocalServerPort
    private Integer port;

    @BeforeAll
    public static void startContainer() {
        startContainers();
    }

    @Test
    void contextLoads() {
        assertNotNull(port);
    }
}
