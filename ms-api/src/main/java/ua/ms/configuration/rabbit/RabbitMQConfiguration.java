package ua.ms.configuration.rabbit;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

@Log4j2
@EnableRabbit
@Configuration
public class RabbitMQConfiguration {
    @Value("${alert-queue.name}")
    private String queue;
    @Value("${alert-queue.exchange}")
    private String exchange;
    @Value("${alert-queue.routing-key}")
    private String routingKey;

    @Bean
    public DirectExchange exchange() {
        log.info(format("Creating new DirectExchange [%s]", exchange));
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queue() {
        log.info(format("Creating new Queue [%s]", queue));
        return new Queue(queue);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        log.info(format("Binding Queue[%s] to Exchange[%s] with routing key[%s]", queue, exchange, routingKey));
        return BindingBuilder.bind(queue)
                .to(exchange).with(routingKey);
    }
}
