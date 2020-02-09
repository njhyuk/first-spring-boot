package com.njhyuk.first.springboot.service.posts;

import com.njhyuk.first.springboot.domain.posts.PostRepository;
import com.njhyuk.first.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postRepository
                .save(requestDto.toEntity())
                .getId();
    }
}
