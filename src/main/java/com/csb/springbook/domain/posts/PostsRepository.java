package com.csb.springbook.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {  //JpaRepository<Entity클래스, PK명>을 상속하면 기본적인 CRUD 메소드 자동 생성
    //Entity클래스와 기본 Entity Repository는 함께 위치해야한다. Entity클래스는 기본 Repository 없이는 제대로 역할을 할 수 없기 떄문.

}
