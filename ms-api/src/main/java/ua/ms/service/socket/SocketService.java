package ua.ms.service.socket;

import java.util.List;

/** Basic operations with socket */
public interface SocketService {

    void registerNewConnection(long userId, List<Long> machineIds);
    void disconnect(long userId);
    int getSessionPool();

}
