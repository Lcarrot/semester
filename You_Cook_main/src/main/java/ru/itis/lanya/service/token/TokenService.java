package ru.itis.lanya.service.token;

import ru.itis.lanya.dto.TokenDto;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.entity.token.RefreshToken;

import java.util.Optional;

public interface TokenService {
    TokenDto getNewTokens(User user);
    Optional<RefreshToken> findByRefreshToken(String token);
    String updateAccessToken(String refreshToken);
    Optional<User> findByAccessToken(String accessToken);
}
