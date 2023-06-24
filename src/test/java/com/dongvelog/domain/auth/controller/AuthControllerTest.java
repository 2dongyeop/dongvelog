package com.dongvelog.domain.auth.controller;

import com.dongvelog.domain.auth.controller.request.LoginRequest;
import com.dongvelog.domain.session.entity.Session;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        ResultActions response = mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andDo(print());

        //then -- 검증
        response.andExpect(status().isOk());
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
        final ResultActions response = mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andDo(print());

        //then -- 검증
        final User loginUser = userRepository.findById(user.getId()).get();

        response.andExpect(status().isOk());
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
        final ResultActions response = mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(request))
                .andDo(print());

        //then -- 검증
        final User loginUser = userRepository.findById(user.getId()).get();

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.accessToken").isNotEmpty());
    }


    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속한다. /foo")
    public void 로그인후권한이필요한페이지에접속() throws Exception {

        //given
        User user = new User(
                "name",
                "email@email.com",
                "1234"
        );
        final Session session = user.addSession();
        userRepository.save(user);


        //when -- 동작
        final ResultActions response
                = mockMvc.perform(get("/foo")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", session.getAccessToken()))
                .andDo(print());

        //then -- 검증
        response.andExpect(status().isOk());
    }


    @Test
    @DisplayName("로그인 후 검증되지않은 세션값으로 접속할 수 ㅇ벗다.")
    public void test123() throws Exception {

        //given
        User user = new User(
                "name",
                "email@email.com",
                "1234"
        );
        final Session session = user.addSession();
        userRepository.save(user);


        //when -- 동작
        final ResultActions response
                = mockMvc.perform(get("/foo")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", session.getAccessToken() + "0da"))
                .andDo(print());

        //then -- 검증
        response.andExpect(status().isUnauthorized());
    }
}