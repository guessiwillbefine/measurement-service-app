package ua.ms.configuration.socket;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;

import static java.lang.String.format;

@Log4j2
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${socket.allowed}")
    private String[] allowedOrigins;

    @Value("${socket.broker.prefixes}")
    private String[] brokerPrefixes;

    @Value("${socket.connection.url}")
    private String connectionEndpoint;

    @Value("${socket.broker.destination}")
    private String destination;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        log.info("Setting message broker for web socket connection");
        config.enableSimpleBroker(brokerPrefixes);
        config.setApplicationDestinationPrefixes(destination);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info(format("registered URL[%s] allowed origins: %s", connectionEndpoint, Arrays.toString(allowedOrigins)));
        registry.addEndpoint(connectionEndpoint).setAllowedOrigins(allowedOrigins);
    }
}