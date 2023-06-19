package com.dongvelog.domain.post.entity;

import lombok.Builder;

public record PostEditor(String title, String content) {

    @Builder
    public PostEditor {
    }
}
