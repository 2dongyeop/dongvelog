package com.dongvelog.domain.auth.service;

import com.dongvelog.domain.auth.controller.request.LoginRequest;
import com.dongvelog.domain.user.entity.User;
import com.dongvelog.domain.user.repository.UserRepository;
import com.dongvelog.global.exception.LoginFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(LoginRequest request) {

        final User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(LoginFailException::new);

        return user.getId();
    }
}
