package com.csb.springbook.web.dto;

import com.csb.springbook.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() { //  form에서 데이터가 모두 완비되는 경우는 toEntity() 같은 식으로 해서 엔티티로 변환하는 방식을 사용
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }


}
