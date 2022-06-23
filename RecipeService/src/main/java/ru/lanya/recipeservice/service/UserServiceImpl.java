package ru.lanya.recipeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanya.recipeservice.models.User;
import ru.lanya.recipeservice.repository.UserRepository;

import java.util.Optional;

@Service
public class  UserServiceImpl implements ru.lanya.recipeservice.service.UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

}
