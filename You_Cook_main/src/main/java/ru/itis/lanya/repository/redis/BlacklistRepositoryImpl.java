package ru.itis.lanya.repository.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BlacklistRepositoryImpl implements BlacklistRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveAccessToken(String accessToken) {
        redisTemplate.opsForValue().set(accessToken, "");
    }

    @Override
    public void saveRefreshToken(String refreshToken) {
        redisTemplate.opsForValue().set(refreshToken, "");
    }

    @Override
    public boolean existsAccessToken(String accessToken) {
        Boolean hasAccessToken = redisTemplate.hasKey(accessToken);
        return hasAccessToken != null && hasAccessToken;
    }

    @Override
    public boolean existsRefreshToken(String refreshToken) {
        Boolean hasRefreshToken = redisTemplate.hasKey(refreshToken);
        return hasRefreshToken != null && hasRefreshToken;
    }
}
