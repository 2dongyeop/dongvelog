package com.dongvelog.domain.post.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class EditPost {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Builder
    public EditPost(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
