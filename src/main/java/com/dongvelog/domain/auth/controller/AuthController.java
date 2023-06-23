package com.dongvelog.domain.auth.controller;

import com.dongvelog.domain.auth.controller.request.LoginRequest;
import com.dongvelog.domain.auth.controller.response.SessionResponse;
import com.dongvelog.domain.auth.service.AuthService;
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

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody LoginRequest loginRequest) {

        //json id,password
        log.info(">>> {}", loginRequest);

        String accessToken = authService.signIn(loginRequest);
        return new SessionResponse(accessToken);
    }
}
