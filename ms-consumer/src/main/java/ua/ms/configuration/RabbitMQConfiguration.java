package ua.ms.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

@Log4j2
@Configuration
public class RabbitMQConfiguration {
    @Value("${alert-queue.name}")
    private String queue;
    @Value("${alert-queue.exchange}")
    private String exchange;
    @Value("${alert-queue.routing-key}")
    private String routingKey;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("rabbit");
    }
    @Bean
    public DirectExchange exchange() {
        log.info(format("Creating new DirectExchange [%s]", exchange));
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queue() {
        log.info(format("Creating new Queue [%s]", queue));
        return new Queue(queue, false);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        log.info(format("Binding Queue[%s] to Exchange[%s] with routing key[%s]", queue, exchange, routingKey));
        return BindingBuilder.bind(queue)
                .to(exchange).with(routingKey);
    }
}
