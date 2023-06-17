package com.dongvelog.domain.post.controller;

import com.dongvelog.domain.post.controller.request.CreatePostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid final CreatePostRequest createPostRequest) {

        return Map.of();
    }
}
