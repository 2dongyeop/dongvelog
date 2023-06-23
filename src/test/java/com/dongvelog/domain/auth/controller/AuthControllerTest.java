package com.dongvelog.domain.auth.controller;

import com.dongvelog.domain.auth.controller.request.LoginRequest;
import com.dongvelog.domain.session.repository.SessionRepository;
import com.dongvelog.domain.user.entity.User;
import com.dongvelog.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인 성공")
    public void 로그인성공() throws Exception {

        //given -- 조건
        userRepository.save(new User(
                "name",
                "email@email.com",
                "1234"
        ));


        String request = objectMapper.writeValueAsString(LoginRequest.builder()
                .email("email@email.com")
                .password("1234")
                .build());

        //when -- 동작
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

        //then -- 검증
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Transactional
    @DisplayName("로그인성공후세션생성확인")
    public void 로그인성공후세션생성확인() throws Exception {

        //given -- 조건
        final User user = userRepository.save(new User(
                "name",
                "email@email.com",
                "1234"
        ));


        final String request = objectMapper.writeValueAsString(LoginRequest.builder()
                .email("email@email.com")
                .password("1234")
                .build());

        //when -- 동작
        final ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

        //then -- 검증
        final User loginUser = userRepository.findById(user.getId()).get();

        response.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(loginUser.getSessions().size()).isEqualTo(1);
    }


    @Test
    @DisplayName("로그인성공후세션응답")
    public void 로그인성공후세션응답() throws Exception {

        //given -- 조건
        final User user = userRepository.save(new User(
                "name",
                "email@email.com",
                "1234"
        ));


        final String request = objectMapper.writeValueAsString(LoginRequest.builder()
                .email("email@email.com")
                .password("1234")
                .build());

        //when -- 동작
        final ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print());

        //then -- 검증
        final User loginUser = userRepository.findById(user.getId()).get();

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty());
    }
}