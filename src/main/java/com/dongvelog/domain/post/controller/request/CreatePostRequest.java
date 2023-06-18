package com.dongvelog.domain.post.controller.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record CreatePostRequest(
        @NotBlank(message = "타이틀을 입력해주세요.") String title,
        @NotBlank(message = "콘텐트를 입력해주세요.") String content) {

    @Builder
    public CreatePostRequest {
    }

    @Override
    public String toString() {
        return "CreatePost{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
