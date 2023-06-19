package com.dongvelog.domain.post.controller.request;

import lombok.Builder;
import lombok.Getter;

import static java.lang.Math.*;

@Getter
@Builder
public final class SearchPost {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}
