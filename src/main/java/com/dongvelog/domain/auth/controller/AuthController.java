package com.dongvelog.domain.auth.controller;

import com.dongvelog.domain.auth.controller.request.SignUpRequest;
import com.dongvelog.domain.auth.service.AuthService;
import com.dongvelog.global.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/logout")
    public void signup(@RequestBody SignUpRequest signUpRequest) {

        authService.signup(signUpRequest);
    }
}
