package com.csb.springbook.web;

import com.csb.springbook.config.auth.SecurityConfig;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*
 * No qualifying bean of type 'com.csb.springbook.config.auth.CustomOAuth2userService' 발생!
 * @WebMvcTest는  @Repository, @Service, @Component를 스캔하지 않는다. 그러므로 SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService는 읽지 못해서 에러 발생!
 * 스캔 대상에서 SecurityConfig를 제거하여 1차 해결
 * => java.lang.IllegalArgumentException: JPA metamodel must not be empty! 에러 추가로 발생!
 *  JPA Auditing을 사용하기 위해선 최소 하나의 @Entity 클래스가 필요한데, 없어서 발생.
 * @EnableJpaAuditing이 메인 어플리케이션에 붙어서 @SpringBootApplication와 함께 있다보니, @WebMvcTest에서도 스캔한다. 그러므로 둘을 분리 시킨다.
 *
 * */
@RunWith(SpringRunner.class) //Junit 외에 다른 실행자 사용시 명시.
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; // HTTP GET, POST등에 대한 API테스트 사용하기 위함.

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  //MockMvc를 통해 /hello 로 get 요청한다.
                .andExpect(status().isOk())     //mvc.perform의 결과를 검증. status가 ok(200코드)인지 검증.
                .andExpect(content().string(hello)); // 본문 내용 검증
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        //given
        String name = "hello";
        int amount = 1000;

        //then
        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount))) //param()의 인자값은 String만 허용되므로, 형변환.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //json 응답값을 필드별로 검증할 수 있는 메소드
                .andExpect(jsonPath("$.amount", is(amount))); // $를 기준으로 필드명을 명시함.
    }


}