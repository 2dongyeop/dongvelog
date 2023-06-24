package com.dongvelog.domain.post.controller;

import com.dongvelog.domain.post.controller.request.CreatePostRequest;
import com.dongvelog.domain.post.controller.request.EditPost;
import com.dongvelog.domain.post.controller.request.SearchPost;
import com.dongvelog.domain.post.controller.response.PostResponse;
import com.dongvelog.domain.post.service.PostService;
import com.dongvelog.global.config.data.UserSession;
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


    @GetMapping("/foo")
    public Long foo(UserSession userSession) {
        log.info(">>> {}", userSession.id());
        return userSession.id();
    }


    @GetMapping("/bar")
    public String bar(UserSession userSession) {
        return "인증이 필요한 페이지";
    }


    @PostMapping("/posts")
    public void post(@RequestBody @Valid final CreatePostRequest createPostRequest) {
        createPostRequest.validate();
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

    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable final Long postId, @RequestBody @Valid final EditPost request) {
        return postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable final Long postId) {
        postService.delete(postId);
    }
}
