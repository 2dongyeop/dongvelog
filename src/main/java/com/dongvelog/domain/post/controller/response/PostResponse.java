package com.dongvelog.domain.post.controller.response;


import com.dongvelog.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class PostResponse {
    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(final Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }

    @Override
    public String toString() {
        return "PostResponse[" +
                "id=" + id + ", " +
                "title=" + title + ", " +
                "content=" + content + ']';
    }
}
