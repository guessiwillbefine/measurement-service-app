package ua.ms.service.mq;

import java.io.Serializable;

/**
 * Message Queue producer
 * @param <T> type of message that will be pushed to queue, must implement Serializable
 * @see java.io.Serializable
 **/
public interface MessageQueueProducer<T extends Serializable> {
    void push(T msg);
}
