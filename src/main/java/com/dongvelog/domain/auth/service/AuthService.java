package com.dongvelog.domain.auth.service;

import com.dongvelog.domain.auth.controller.request.SignUpRequest;
import com.dongvelog.domain.user.entity.User;
import com.dongvelog.domain.user.repository.UserRepository;
import com.dongvelog.global.exception.AlreadyExistEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signup(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistEmailException();
        }


        final User user = new User(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                null);

        userRepository.save(user);
    }
}
