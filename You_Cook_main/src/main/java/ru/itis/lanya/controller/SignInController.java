package ru.itis.lanya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.lanya.entity.Response;
import ru.itis.lanya.form.UserForm;
import ru.itis.lanya.service.UserService;

@RestController
@PreAuthorize("permitAll()")
public class SignInController {

    @Autowired
    private UserService userService;

    @PostMapping("sign-in")
    public Response signIn(@RequestBody UserForm userForm){
        return userService.signIn(userForm);
    }
}
