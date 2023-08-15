package com.dongvelog.domain.auth.service;

import com.dongvelog.domain.auth.controller.request.SignUpRequest;
import com.dongvelog.domain.user.entity.User;
import com.dongvelog.domain.user.repository.UserRepository;
import com.dongvelog.global.exception.AlreadyExistEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(final SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistEmailException();
        }

        final String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        final User user = new User(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                encodedPassword);

        userRepository.save(user);
    }

//    public Long login(Login login) {
//        User user = userRepository.findByEmail(login.getEmail())
//                .orElseThrow(InvalidSigninInformation::new);
//
//        PasswordEncoder encoder = new PasswordEncoder();
//        var matches = encoder.matches(login.getPassword(), user.getPassword());
//        if (!matches) {
//            throw new InvalidSigninInformation();
//        }
//
//        return user.getId();
//    }
}
