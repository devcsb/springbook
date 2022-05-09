package com.csb.springbook.web;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) //Junit 외에 다른 실행자 사용시 명시.
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; // HTTP GET, POST등에 대한 API테스트 사용하기 위함.

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  //MockMvc를 통해 /hello 로 get 요청한다.
                .andExpect(status().isOk())     //mvc.perform의 결과를 검증. status가 ok(200코드)인지 검증.
                .andExpect(content().string(hello)); // 본문 내용 검증
    }
    
    @Test
    public void helloDto가_리턴된다() throws Exception{
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