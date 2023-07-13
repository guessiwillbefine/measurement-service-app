package ua.ms.service.cash;

import java.util.Map;

/**
 * Provides access to Redis Storage
 * K - key
 * V - value
 */
public interface RedisService<K, V> {

    void saveData(K key, V value);

    V getData(K key);

    Map<K, V> getAll();

    boolean removeData(K key);

    void clear();
}
