package com.example.securityotp.repository;

import com.example.securityotp.dto.PostDto;
import com.example.securityotp.dto.post.PostSearchDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.securityotp.entity.QPost.*;

@RequiredArgsConstructor
@Repository
public class PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<PostDto> findAll(PostSearchDto condition, Pageable pageable) {
        List<PostDto> content = queryFactory
                .select(Projections.fields(PostDto.class,
                                post.id,
                                post.title,
                                post.content))
                .from(post)
                .where(titleContains(condition.getTitle()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(post.count())
                .from(post)
                .where(titleContains(condition.getTitle()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? post.title.contains(title) : null;
    }
}
