package ua.ms.consumer;

/**
   marks class as message queue consumer
   @param <T> - type of message that will be read and proceed by consumer
 **/
public interface MessageQueueConsumer<T> {
    void consume(T msg);
}
