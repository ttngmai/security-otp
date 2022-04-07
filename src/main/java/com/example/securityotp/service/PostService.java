package com.example.securityotp.service;

import com.example.securityotp.dto.PostDto;
import com.example.securityotp.dto.post.PostSearchDto;
import com.example.securityotp.entity.Post;
import com.example.securityotp.mapper.PostMapper;
import com.example.securityotp.repository.PostQueryRepository;
import com.example.securityotp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final PostMapper postMapper;
    private final EntityManager em;

    @Transactional(readOnly = true)
    public Page<PostDto> findAllPosts(PostSearchDto condition, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);

        return postQueryRepository.findAll(condition, pageable);
    }

    @Transactional(readOnly = true)
    public PostDto findPost(Long id) {
        Post post = postRepository.findById(id).get();

        if (post == null) {
            return null;
        }

        PostDto postDto = postMapper.entityToDto(post);

        return postDto;
    }

    @Transactional
    public PostDto editAndCreate(PostDto postDto) {
        Post post = Post
                .builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        Post entity = postRepository.save(post);

        PostDto newPost = postMapper.entityToDto(entity);

        return newPost;
    }
}
