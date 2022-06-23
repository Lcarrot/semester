package ru.itis.lanya.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.entity.redis.RedisUser;
import ru.itis.lanya.repository.UserRepository;
import ru.itis.lanya.repository.redis.RedisUserRepository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RedisUserServiceImpl implements RedisUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @Autowired
    private RedisUserRepository redisUserRepository;

    @Override
    public void addTokenToUser(User user, String accessToken, String refreshToken) {
        String redisId = user.getRedisId();

        RedisUser redisUser;
        if(redisId != null){
            redisUser = redisUserRepository.findById(redisId).orElseThrow(IllegalArgumentException::new);
            if(redisUser.getAccessTokens() == null){
                redisUser.setAccessTokens(new ArrayList<>());
            }
            redisUser.getAccessTokens().add(accessToken);
            if (redisUser.getRefreshTokens() == null){
                redisUser.setRefreshTokens(new ArrayList<>());
            }
            redisUser.getRefreshTokens().add(refreshToken);
        } else {
            redisUser = RedisUser.builder()
                    .userId(user.getId())
                    .accessTokens(Collections.singletonList(accessToken))
                    .build();
        }
        redisUserRepository.save(redisUser);
        user.setRedisId(redisUser.getId());
        userRepository.save(user);
    }

    @Override
    public void addAllTokensToBlacklist(User user) {
        if (user.getRedisId() != null){
            RedisUser redisPerson = redisUserRepository.findById(user.getRedisId()).orElseThrow(IllegalArgumentException::new);
            List<String> accessTokens = redisPerson.getAccessTokens();
            List<String> refreshTokens = redisPerson.getRefreshTokens();
            for(String accessToken : accessTokens){
                jwtBlacklistService.addAccessToken(accessToken);
            }
            redisPerson.getAccessTokens().clear();
            for(String refreshToken : refreshTokens){
                jwtBlacklistService.addRefreshToken(refreshToken);
            }
            redisPerson.getRefreshTokens().clear();
            redisUserRepository.save(redisPerson);
        }
    }
}
