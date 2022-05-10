package com.csb.springbook.service.posts;


import com.csb.springbook.domain.posts.Posts;
import com.csb.springbook.domain.posts.PostsRepository;
import com.csb.springbook.web.dto.PostResponseDto;
import com.csb.springbook.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    /*
    * 글 저장
    * */
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /*
    * 글 수정
    * */
    @Transactional
    public Long update(Long id, PostsSaveRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /*
    * 글 조회
    * */
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostResponseDto(entity);
    }


}
