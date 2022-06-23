package ru.itis.lanya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.lanya.entity.Response;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.form.UserForm;
import ru.itis.lanya.repository.UserRepository;
import ru.itis.lanya.service.redis.RedisUserService;
import ru.itis.lanya.service.token.TokenService;

import java.util.List;
import java.util.Optional;

@Service
public class  UserServiceImpl implements UserService{

    @Autowired
    private RedisUserService redisUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public Response signUp(User user) {
        user.setRole(User.Role.USER);
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());
        if (userByUsername.isEmpty() && userByEmail.isEmpty()){
            userRepository.save(user);
            return Response.builder()
                    .success(true)
                    .response("Пользователь с ником " + user.getUsername()
                            + " и почтой " + user.getEmail()
                            + "успешно зарегистрирован!")
                    .build();
        }
        if (userByUsername.isPresent() && userByEmail.isPresent()){
            return Response.builder()
                    .success(false)
                    .response("Пользователь с ником " + user.getUsername()
                            + " и почтой " + user.getEmail()
                            + "уже существует!")
                    .build();
        }
        if (userByUsername.isPresent()){
            return Response.builder()
                    .success(false)
                    .response("Пользователь с ником " + user.getUsername()
                            + "уже существует!")
                    .build();
        }
        return Response.builder()
                .success(false)
                .response("Пользователь с почтой " + user.getEmail()
                        + "уже существует!")
                .build();
    }

    @Override
    public Response signIn(UserForm userForm) {
        Optional<User> user = userRepository.findByEmail(userForm.getEmail());
        if(user.isPresent() && passwordEncoder.matches(userForm.getPassword(), user.get().getPassword())){
            blockAllTokens(user.get().getId());
            return Response.builder()
                    .success(true)
                    .response(tokenService.getNewTokens(user.get()))
                    .build();
        }
        return Response.builder()
                .success(false)
                .response("Неверный логин или пароль!")
                .build();
    }

    @Override
    public void blockAllTokens(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> redisUserService.addAllTokensToBlacklist(value));
    }

    @Override
    public Optional<User> findByAccessToken(String accessToken) {
        return tokenService.findByAccessToken(accessToken);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public boolean subscribe(User from, Long to) {
        User toUser = userRepository.findById(to).get();
        userRepository.delete(toUser);
        List<User> subscribers = toUser.getSubscribers();
        if (subscribers.contains(from)){
            return false;
        }
        subscribers.add(from);
        toUser.setSubscribers(subscribers);
        userRepository.save(toUser);
        userRepository.delete(from);
        List<User> subscriptions = from.getSubscriptions();
        subscriptions.add(toUser);
        from.setSubscriptions(subscriptions);
        userRepository.save(from);
        return true;
    }


}
