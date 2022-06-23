package ru.itis.lanya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.lanya.service.token.TokenService;

@RestController
@PreAuthorize("isAuthenticated()")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestHeader("R-TOKEN") String refreshToken){
        return ResponseEntity.ok("Your new access token: " + tokenService.updateAccessToken(refreshToken));
    }
}
