package com.dongvelog.domain.post.repository;

import com.dongvelog.domain.post.controller.request.SearchPost;
import com.dongvelog.domain.post.entity.Post;
import com.dongvelog.domain.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class PostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(final SearchPost searchPost) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(searchPost.getSize())
                .offset(searchPost.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
