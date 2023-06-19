package com.dongvelog.domain.post.repository;

import com.dongvelog.domain.post.controller.request.SearchPost;
import com.dongvelog.domain.post.entity.Post;

import java.util.List;

public interface CustomPostRepository {

    List<Post> getList(final SearchPost searchPost);
}
