package com.dongvelog.domain.post.service;

import com.dongvelog.domain.post.controller.request.CreatePostRequest;
import com.dongvelog.domain.post.controller.request.EditPost;
import com.dongvelog.domain.post.controller.request.SearchPost;
import com.dongvelog.domain.post.controller.response.PostResponse;
import com.dongvelog.domain.post.entity.Post;
import com.dongvelog.domain.post.repository.PostRepository;
import com.dongvelog.global.exception.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThat(postResponse.getTitle()).isEqualTo(savedPost.getTitle());
    }


    @Test
    @DisplayName("글 1페이지 조회 조회")
    public void 글_여러개_페이징조회() throws Exception {
        //given -- 조건
        final List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("동벨롭 : " + i)
                        .content("취업 " + i + "일차 회고록")
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        final SearchPost searchPost = SearchPost.builder()
                .page(1)
                .build();

        //when -- 동작
        final List<PostResponse> posts = postService.getList(searchPost);

        //then -- 검증
        assertThat(posts.size()).isEqualTo(10);
        assertThat(posts.get(0).getTitle()).isEqualTo("동벨롭 : 19");
    }


    @Test
    @DisplayName("글 제목 수정")
    public void 글_제목_수정() throws Exception {
        //given -- 조건
        final Post post = postRepository.save(Post.builder()
                .title("이동엽")
                .content("반포자이")
                .build());

        postRepository.save(post);

        final EditPost editPost = EditPost.builder()
                .title("이동걸")
                .build();

        //when -- 동작
        postService.edit(post.getId(), editPost);

        //then -- 검증
        final Post findPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertThat(findPost.getTitle()).isEqualTo("이동걸");
    }


    @Test
    @DisplayName("글 내용 수정2 : 본문만 수정할거면 제목에 Null을 넘긴다. 이때 제목은 안바뀌어야함")
    public void 글_내용_수정2() throws Exception {
        //given -- 조건
        final Post post = postRepository.save(Post.builder()
                .title("이동엽")
                .content("반포자이")
                .build());

        postRepository.save(post);

        final EditPost editPost = EditPost.builder()
                .title(null)
                .content("둥지아파트")
                .build();

        //when -- 동작
        postService.edit(post.getId(), editPost);

        //then -- 검증
        final Post findPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertThat(findPost.getTitle()).isEqualTo("이동엽");
        assertThat(findPost.getContent()).isEqualTo("둥지아파트");
    }


    @Test
    public void 게시글삭제() throws Exception {
        //given -- 조건
        final Post post = postRepository.save(Post.builder()
                .title("이동엽")
                .content("반포자이")
                .build());

        postRepository.save(post);

        //when -- 동작
        postService.delete(post.getId());

        //then -- 검증
        assertThat(postRepository.count()).isEqualTo(0);
    }


    @Test
    @DisplayName("글 1개 조회 실패 테스트")
    public void 글조회실패시() throws Exception {
        //given -- 조건
        postRepository.save(Post.builder()
                .title("제목")
                .content("내용")
                .build());

        //expected
        final PostNotFoundException e = assertThrows(PostNotFoundException.class, () ->
                postService.get(100L), "예외처리가 잘못 되었어요.");

        assertThat(e.getMessage()).isEqualTo("존재하지 않는 글입니다.");
    }
}