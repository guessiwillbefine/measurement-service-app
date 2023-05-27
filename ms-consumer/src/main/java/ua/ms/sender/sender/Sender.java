package ua.ms.sender.sender;

public interface Sender<T> {
    void send(T alertDto);
}
