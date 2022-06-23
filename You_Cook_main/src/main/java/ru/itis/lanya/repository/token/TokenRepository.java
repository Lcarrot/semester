package ru.itis.lanya.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.entity.token.RefreshToken;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);
    void delete(RefreshToken refreshToken);
    Optional<RefreshToken> findByUser(User user);
}
