package com.csb.springbook.config.auth;

import com.csb.springbook.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  //Spring Security 설정들을 활성화 시켜준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2userService customOAuth2userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .headers().frameOptions().disable()  //h2 콘솔 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeRequests()  //URL별 권한 관리를 설정하는 옵션의 시작점. authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
                    // antMatchers : 권한 관리 대상을 지정하는 옵션. URL, HTTP 메소드 별로 관리 가능.
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()  // 해당 URL에는 전체 열람 권한 부여.
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())  // USER 권한을 가진 사람만 접근 가능하게 설정.
                    .anyRequest().authenticated()  // anyRequest() : 설정된 값들 이외 나머지 URL들을 나타낸다.  // authenticated()를 추가하여 나머지 URL엔 모두 인증된(로그인 된) 사용자들만 허용.
                .and()
                    .logout()  //로그아웃 설정에 대한 진입점.
                        .logoutSuccessUrl("/")  //로그아웃 성공 시 / 주소로 이동
                .and()
                    .oauth2Login()//OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint()  //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당.
                            .userService(customOAuth2userService);  //소셜 로그인 성공 시 후속 조치를 진행항 UserService 인터페이스의 구현체를 등록.
                                                        //리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시가능.
    }
}
