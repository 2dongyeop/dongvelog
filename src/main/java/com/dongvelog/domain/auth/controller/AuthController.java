package com.dongvelog.domain.auth.controller;

import com.dongvelog.domain.auth.controller.request.LoginRequest;
import com.dongvelog.domain.auth.controller.request.SignUpRequest;
import com.dongvelog.domain.auth.controller.response.SessionResponse;
import com.dongvelog.domain.auth.service.AuthService;
import com.dongvelog.global.config.AppConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody LoginRequest loginRequest) {
        Long userId = authService.signIn(loginRequest);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws);
    }


    @PostMapping("/auth/logout")
    public void signup(@RequestBody SignUpRequest signUpRequest) {

        authService.signup(signUpRequest);
    }
}
