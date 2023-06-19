package com.dongvelog.domain.post.controller;

import com.dongvelog.domain.post.controller.request.CreatePostRequest;
import com.dongvelog.domain.post.controller.request.SearchPost;
import com.dongvelog.domain.post.controller.response.PostResponse;
import com.dongvelog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid final CreatePostRequest createPostRequest) {
        postService.write(createPostRequest);
    }


    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") final Long id) {
        return postService.get(id);
    }


    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute final SearchPost searchPost) {

        return postService.getList(searchPost);
    }
}
