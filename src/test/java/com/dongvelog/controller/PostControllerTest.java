package com.dongvelog.controller;

import com.dongvelog.domain.post.controller.request.CreatePostRequest;
import com.dongvelog.domain.post.entity.Post;
import com.dongvelog.domain.post.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    public void test() throws Exception {

        //given
        final CreatePostRequest request = CreatePostRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        final String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    public void test2() throws Exception {

        //given
        final CreatePostRequest request = CreatePostRequest.builder()
                .content("내용입니다.")
                .build();

        final String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    public void test3() throws Exception {
        //given
        final CreatePostRequest request = CreatePostRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        final String jsonRequest = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);

        final Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목입니다.");
        assertThat(post.getContent()).isEqualTo("내용입니다.");
    }


    @Test
    @DisplayName("글 1개 조회")
    public void test4() throws Exception {
        //given -- 조건
        final Post post = Post.builder()
                .title("123456789012345")
                .content("content")
                .build();
        postRepository.save(post);

        //when -- 동작
        //then -- 검증
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("content"))
                .andDo(print());
    }


    @Test
    public void 글_여러개_페이징_querydsl_조회() throws Exception {
        //given -- 조건
        final List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("동벨롭 : " + i)
                        .content("취업 " + i + "일차 회고록")
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].id").value(20))
                .andExpect(jsonPath("$[0].title").value("동벨롭 : 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청해도 첫 페이지를 가져온다.(현재 1이 기본값)")
    public void 글_여러개_페이징_querydsl_조회2() throws Exception {
        //given -- 조건
        final List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("동벨롭 : " + i)
                        .content("취업 " + i + "일차 회고록")
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("동벨롭 : 19"))
                .andDo(print());
    }
}