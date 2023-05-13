package ua.ms.service.socket;

/** Service to send message with measure stats to client */
public interface MeasureSocketService extends SocketService {
    void sendMeasureResults();
}
