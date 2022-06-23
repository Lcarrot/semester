package ru.itis.lanya.service.redis;

public interface JwtBlacklistService {
    void addAccessToken(String accessToken);

    void addRefreshToken(String refreshToken);

    boolean existsAccessToken(String accessToken);

    boolean existsRefreshToken(String refreshToken);
}
