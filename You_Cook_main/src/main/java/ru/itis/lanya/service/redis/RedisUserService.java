package ru.itis.lanya.service.redis;


import ru.itis.lanya.entity.User;

public interface RedisUserService {
    void addTokenToUser(User user, String accessToken, String refreshToken);

    void addAllTokensToBlacklist(User user);
}
