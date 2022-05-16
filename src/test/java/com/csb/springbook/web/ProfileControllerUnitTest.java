package com.csb.springbook.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class ProfileControllerUnitTest {
/*
* 생성자 DI의 유용성을 체감할 수 있다.
* Enviroment를 @Autowired로 DI 받았다면 이런 테스트 코드를 작성하지 못하고 항상 스프링을 띄워서 테스트 해야 했을 것.
* */
   @Test
   public void real_profile이_조회된다() throws Exception{
       //given
       String expectedProfile = "real";
       MockEnvironment env = new MockEnvironment();
       env.addActiveProfile(expectedProfile);
       env.addActiveProfile("oauth");
       env.addActiveProfile("real-db");

       ProfileController controller = new ProfileController(env);
       //when
       String profile = controller.profile();

       //then
       assertThat(profile).isEqualTo(expectedProfile);
   }
   
   @Test
   public void real_profile이_없으면_첫번째가_조회된다() throws Exception{
       //given
       String expectedProfile = "oauth";
       MockEnvironment env = new MockEnvironment();

       env.addActiveProfile(expectedProfile);
       env.addActiveProfile("real-db");

       ProfileController controller = new ProfileController(env);

       //when
       String profile = controller.profile();
       
       //then
       assertThat(profile).isEqualTo(expectedProfile);
   }

    @Test
    public void active_profile이_없으면_default가_조회된다() {
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }


}