package com.csb.springbook.web.dto;

import com.csb.springbook.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostResponseDto(Posts entity) { //생성자로 Posts Entity를 받아 Dto 값 생성.
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
