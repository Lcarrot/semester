package ru.itis.lanya.repository.redis;

public interface BlacklistRepository{

    void saveAccessToken(String accessToken);

    void saveRefreshToken(String refreshToken);

    boolean existsAccessToken(String accessToken);

    boolean existsRefreshToken(String refreshToken);
}
