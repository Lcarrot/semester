package ru.itis.lanya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.lanya.entity.Response;
import ru.itis.lanya.entity.User;
import ru.itis.lanya.service.UserService;

@RestController
@PreAuthorize("permitAll()")
public class SignUpController {

    @Autowired
    private UserService userService;

    @PostMapping("sign-up")
    public Response signUp(@RequestBody User user){
        return userService.signUp(user);
    }
}
