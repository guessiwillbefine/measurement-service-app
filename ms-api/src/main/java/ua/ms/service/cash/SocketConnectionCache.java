package ua.ms.service.cash;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of a cache for storing WebSocket connections to send current measurements to the frontend.
 * We store the user identifier as the key and a list of machine identifiers from which we take sensor measurements for sending as the value.
 */
@Service
@RequiredArgsConstructor
public class SocketConnectionCache implements RedisService<String, List<Long>> {

    private final RedisTemplate<String, List<Long>> redisTemplate;

    @Override
    public void saveData(String key, List<Long> data) {
        redisTemplate.opsForValue().set(key, data);
    }

    @Override
    public List<Long> getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Map<String, List<Long>> getAll() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys == null) {
            return Collections.emptyMap();
        }
        return keys.stream().collect(Collectors.toMap(key -> key, this::getData));
    }

    @Override
    public boolean removeData(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
