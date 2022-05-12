package com.csb.springbook.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass  //상속받는 자식클래스에세 매핑 정보만 제공하는 클래스임을 명시. Entity가 이 클래스를 상속받으면 이 클래스의 필드들도 칼럼으로 인식하도록 함.
@EntityListeners(AuditingEntityListener.class)  //BaseTimeEntiy 클래스에 Auditing 기능 추가
public abstract class BaseTimeEntity {  //추상 클래스 만들고, Enity에 상속시킴

    @CreatedDate  //Entity가 생성되어 자장될 때 시간이 자동 저장된다.
    private LocalDateTime createdDate;

    @LastModifiedDate  //조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
    private LocalDateTime modifiedDate;
}
