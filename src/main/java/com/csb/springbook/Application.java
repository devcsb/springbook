package com.csb.springbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //이 어노테이션이 붙은 위치부터 설정을 읽으므로, 항상 최상단에 위치해야함.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); //내장WAS를 실행
    }
}
