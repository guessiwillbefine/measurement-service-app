package ms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestContainersConfig {

    @BeforeAll
    public void setup() {
        GenericContainer<?> container = new GenericContainer<>("mysql:5-debian")
                .withExposedPorts(8080); // Пример конфигурации контейнера

        container.start(); // Запуск контейнера

        // Другие действия, необходимые для настройки вашего приложения
    }

}
