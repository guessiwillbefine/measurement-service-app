package ms;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
public abstract class TestContainersIntegrationTest {

    protected static final int DEFAULT_REDIS_PORT = 6379;

    protected static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:5-debian"));

    protected static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
            .withExposedPorts(DEFAULT_REDIS_PORT);
    protected static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    protected static final RabbitMQContainer rabbitContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.10.7-management"));
    protected static final List<GenericContainer<?>> containers = List.of(mySQLContainer, redisContainer, mongoDBContainer, rabbitContainer);
    /**
     * Copies properties from docker containers to Spring properties
     */
    @DynamicPropertySource
    public static void setProps(DynamicPropertyRegistry registry) {

        /* mysql */
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);

        /* mongodb */
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getExposedPorts);

        /* redis */
        registry.add("redis.host", redisContainer::getHost);
        registry.add("redis.port", () -> redisContainer.getMappedPort(DEFAULT_REDIS_PORT));

        /* rabbit */
        registry.add("spring.rabbitmq.host", rabbitContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitContainer::getAdminPassword);
    }

    protected static void startContainers() {
        for (GenericContainer<?> container : containers) {
            container.start();
        }
    }

}
