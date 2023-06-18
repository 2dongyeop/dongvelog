package com.dongvelog.domain.post.service;

import com.dongvelog.domain.post.controller.request.CreatePostRequest;
import com.dongvelog.domain.post.controller.response.PostResponse;
import com.dongvelog.domain.post.entity.Post;
import com.dongvelog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    public void write() throws Exception {
        //given -- 조건
        final CreatePostRequest request = CreatePostRequest.builder()
                .title("제목")
                .content("내용")
                .build();

        //when -- 동작
        postService.write(request);

        //then -- 검증
        assertThat(postRepository.count()).isEqualTo(1L);
    }


    @Test
    @DisplayName("글 1개 조회")
    public void 글조회() throws Exception {
        //given -- 조건
        final Post savedPost = postRepository.save(Post.builder()
                .title("제목")
                .content("내용")
                .build());

        //when -- 동작
        final PostResponse postResponse = postService.get(savedPost.getId());

        //then -- 검증
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.title()).isEqualTo(savedPost.getTitle());
    }


    @Test
    @DisplayName("글 1페이지 조회 조회")
    public void 글페이징조회() throws Exception {
        //given -- 조건
        final List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("동벨롭 : " + i)
                        .content("취업 " + i + "일차 회고록")
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        final Pageable pageable = PageRequest.of(0, 5, DESC, "id");

        //when -- 동작
        final List<PostResponse> posts = postService.getList(pageable);

        //then -- 검증
        assertThat(posts.size()).isEqualTo(5);
        assertThat(posts.get(0).title()).isEqualTo("동벨롭 : 30");
    }
}