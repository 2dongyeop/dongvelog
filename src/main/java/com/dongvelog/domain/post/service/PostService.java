package com.dongvelog.domain.post.service;

import com.dongvelog.domain.post.controller.request.CreatePostRequest;
import com.dongvelog.domain.post.controller.request.SearchPost;
import com.dongvelog.domain.post.controller.response.PostResponse;
import com.dongvelog.domain.post.entity.Post;
import com.dongvelog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(final CreatePostRequest request) {
        postRepository.save(Post.builder()
                .title(request.title())
                .content(request.content())
                .build());
    }

    public PostResponse get(final Long id) {

        final Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(final SearchPost searchPost) {
        return postRepository.getList(searchPost).stream()
                .map(getPostResponse())
                .toList();
    }

    private Function<Post, PostResponse> getPostResponse() {
        return post -> PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
