package ru.itis.lanya.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.lanya.repository.redis.BlacklistRepository;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Override
    public void addAccessToken(String accessToken) {
        blacklistRepository.saveAccessToken(accessToken);
    }

    @Override
    public void addRefreshToken(String refreshToken) {
        blacklistRepository.saveRefreshToken(refreshToken);
    }

    @Override
    public boolean existsAccessToken(String accessToken) {
        return blacklistRepository.existsAccessToken(accessToken);
    }

    @Override
    public boolean existsRefreshToken(String refreshToken) {
        return blacklistRepository.existsRefreshToken(refreshToken);
    }

}
