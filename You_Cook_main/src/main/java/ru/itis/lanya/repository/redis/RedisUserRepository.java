package ru.itis.lanya.repository.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.lanya.entity.redis.RedisUser;

public interface RedisUserRepository extends KeyValueRepository<RedisUser, String> {
}
