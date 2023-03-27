package ua.ms;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MSConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MSConsumerApplication.class, args);
    }

}
