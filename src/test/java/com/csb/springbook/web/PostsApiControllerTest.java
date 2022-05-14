package com.csb.springbook.web;

import com.csb.springbook.domain.posts.Posts;
import com.csb.springbook.domain.posts.PostsRepository;
import com.csb.springbook.web.dto.PostsSaveRequestDto;
import com.csb.springbook.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
* Expecting: <302 FOUND>to be equal to:<200 OK> 에러 발생!
* 스프링 시큐리티는 인증되지 않은 사용자의 요청은 redirect시키기 떄문. 임의의 인증된 사용자를 추가해서 테스트 구동하자! (spring-security-test 활용)
*
* */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  //랜덤 포트를 열어준다. 열려있는 포트가 없다면 TestRestTemplate을 빈으로 등록할 수 없기 때문.
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private PostsRepository postsRepository;
    @Autowired private WebApplicationContext context;

    private MockMvc mvc;

    /* @Before
      : 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성 */
    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test                          //가짜 유저 생성후, roles 값을 USER로 주어 인증된 사용자를 만듬.
    @WithMockUser(roles = "USER") // WithMockUser는 MockMvc에서만 작동하는데, 현재는 @SpringBootTest를 사용중이므로 따로 코드를 추가해줘야함.
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        /* mvc.perform: 생성된 MockMvc를 통해 API를 테스트한다.
            본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환 */
        //when
        mvc.perform(post(url)  // MockMvcRequestBuilders.post
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("제목")
                .content("내용")
                .author("csb")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "수정된 제목";
        String expectedContent = "수정된 내용";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        System.out.println("url = " + url);

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        // //ResponseEntity => 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함한다
        // // exchange : HTTP 메서드를 지정해서 요청을 보내고 타입에 맞게 응답객체 반환. (url, httpMethod, 요청객체, 응답타입).

        mvc.perform(put(url)  //MockMvcRequestBuilders.put()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);


    }


}