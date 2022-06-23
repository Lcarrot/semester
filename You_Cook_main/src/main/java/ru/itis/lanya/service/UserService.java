package ru.itis.lanya.service;

import ru.itis.lanya.entity.Response;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.form.UserForm;
import java.util.Optional;

public interface UserService {
    Response signUp(User user);
    Response signIn(UserForm userForm);
    void blockAllTokens(Long userId);
    Optional<User> findByAccessToken(String accessToken);
    User findById(Long userId);
    boolean subscribe(User from, Long to);
}
