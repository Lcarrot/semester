package ru.itis.lanya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.lanya.dto.UserDtoForMyself;
import ru.itis.lanya.dto.UserDtoForOther;
import ru.itis.lanya.entity.Response;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.service.UserService;

import java.util.Objects;

@RestController
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "profile/{user-id}")
    public Response getProfile(@PathVariable("user-id") Long userId, @RequestHeader("A-TOKEN") String accessToken){
        User user = userService.findByAccessToken(accessToken).get();
        if (Objects.equals(user.getId(), userId)){
            return Response.builder()
                    .success(true)
                    .response(UserDtoForMyself.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .telegram(user.getTelegramId())
                            .recipes(user.getRecipes())
                            .subscribersCount((long) user.getSubscribers().size())
                            .subscribers(user.getSubscribers())
                            .subscriptions(user.getSubscriptions())
                            .build())
                    .build();
        }
        return Response.builder()
                .success(true)
                .response(UserDtoForOther.builder()
                        .username(user.getUsername())
                        .subscribers((long) user.getSubscribers().size())
                        .recipes(user.getRecipes())
                        .build())
                .build();
    }

    @PostMapping(value = "profile/{user-id}/subscribe")
    public Response subscribe(@PathVariable("user-id") Long userId,
                              @RequestHeader("A-TOKEN") String accessToken){
        User user = userService.findByAccessToken(accessToken).get();
        if (Objects.equals(user.getId(), userId)){
            return Response.builder()
                    .success(false)
                    .response("Вы не можете подписаться сами на себя!")
                    .build();
        }
        if (userService.subscribe(user, userId)){
            return Response.builder()
                    .success(true)
                    .response("Вы подписались!")
                    .build();
        }
        return Response.builder()
                .success(false)
                .response("Вы уже подписаны!")
                .build();
    }


}
