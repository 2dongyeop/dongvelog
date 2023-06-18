package com.dongvelog.domain.post.controller.response;


import lombok.Builder;

public record PostResponse(Long id, String title, String content) {

    @Builder
    public PostResponse(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
