package ms;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
public abstract class TestContainersIntegrationTest {

    protected static final RedisProperties REDIS_PROPERTIES = new RedisProperties();

    protected static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:5-debian"))
            .withReuse(true);;

    protected static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
            .withExposedPorts(REDIS_PROPERTIES.getPort())
            .withReuse(true);

    protected static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10")
            .withReuse(true);;

    protected static final RabbitMQContainer rabbitContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.10.7-management"))
            .withReuse(true);;

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
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

        /* redis */
        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(REDIS_PROPERTIES.getPort()));

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
