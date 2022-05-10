package com.csb.springbook.domain.posts;

import com.csb.springbook.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor  //파라미터 없는 기본 생성자 생성. final 필드 포함 X
@Entity //테이블과 링크될 클래스임을 명시.
public class Posts extends BaseTimeEntity {  //

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //타입을 TEXT로 변경
    private String content;

    private String author;

    @Builder  //생성자 상단에 선언시, 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
