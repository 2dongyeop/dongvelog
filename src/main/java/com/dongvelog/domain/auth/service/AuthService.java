package com.dongvelog.domain.auth.service;

import com.dongvelog.domain.auth.controller.request.LoginRequest;
import com.dongvelog.domain.auth.controller.request.SignUpRequest;
import com.dongvelog.domain.user.entity.User;
import com.dongvelog.domain.user.repository.UserRepository;
import com.dongvelog.global.crypto.PasswordEncoder;
import com.dongvelog.global.exception.AlreadyExistEmailException;
import com.dongvelog.global.exception.InvalidSigninInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long signIn(LoginRequest request) {

        final User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        boolean result = encoder.matches(request.getPassword(), user.getPassword());
        if (!result) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

    public void signup(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistEmailException();
        }

        final String encryptedPassword = encoder.encode(signUpRequest.getPassword());

        final User user = new User(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                encryptedPassword);

        userRepository.save(user);
    }
}
