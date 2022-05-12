package com.csb.springbook.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

/*
 (소셜로그인 적용 후, 설정파일 수정 전)
* No qualifying bean of type 'com.csb.springbook.config.auth.CustomOAuth2userService' 에러 발생!
* CustomOAuth2userService를 생성하는데 필요한 소셜 로그인 관련 설정값들이 없어서 발생하는 에러.
* src/test 만의 설정파일이 없으면, main의 application.yml을 가져오지만, application-outh.yml 같은 추가 profile 설정파일은 가져오지 않기 때문.
* */
public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name); // assertThat() 검증하고 싶은 대상을 인자로 받음
        assertThat(dto.getAmount()).isEqualTo(amount); // isEqualTo 동등 비교 메소드

    }

}