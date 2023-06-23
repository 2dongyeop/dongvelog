package com.dongvelog.domain.auth.controller;

import com.dongvelog.domain.auth.controller.request.LoginRequest;
import com.dongvelog.domain.user.entity.User;
import com.dongvelog.domain.user.repository.UserRepository;
import com.dongvelog.global.exception.LoginFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public void login(@RequestBody LoginRequest loginRequest) {

        //json id,password
        log.info(">>> {}", loginRequest);


        //db select
        final User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(LoginFailException::new);

        //response token
    }
}
