package com.csb.springbook.web;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate; // REST API 호출이후 응답을 받을 때까지 기다리는 동기방식의 템플릿
    
    @Test
    public void 메인페이지_로딩() throws Exception{
        //when
        String body = this.restTemplate.getForObject("/", String.class);  //주어진 URL 주소로 HTTP GET 메서드 보내고 명시한 타입의 객체로 결과를 반환받는다

        //then

        assertThat(body).contains("스프링부트로 시작하는 웹 서비스");
    }

}