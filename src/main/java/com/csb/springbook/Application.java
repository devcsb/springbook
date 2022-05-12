package com.csb.springbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing // JPA Auditing 관련 어노테이션 활성화 // 테스트코드 오류 해결을 위하여 @SpringBootApplication과 분리하였음.
@SpringBootApplication //이 어노테이션이 붙은 위치부터 설정을 읽으므로, 항상 최상단에 위치해야함.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); //내장WAS를 실행
    }
}
