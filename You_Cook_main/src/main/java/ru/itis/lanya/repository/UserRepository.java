package ru.itis.lanya.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.lanya.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}

