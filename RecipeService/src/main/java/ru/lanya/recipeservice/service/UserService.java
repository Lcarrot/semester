package ru.lanya.recipeservice.service;

import ru.lanya.recipeservice.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long userId);
}
