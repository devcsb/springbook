package com.csb.springbook.config.auth;

import com.csb.springbook.config.auth.dto.OAuthAttributes;
import com.csb.springbook.config.auth.dto.SessionUser;
import com.csb.springbook.domain.user.User;
import com.csb.springbook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 소셜로그인 이후 가져온 사용자 정보를 기반으로 가입, 정보수정, 세션 저장등의 기능을 담당
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2userService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);  //OAuth2User 정보 담음

        String registrationId = userRequest.getClientRegistration().getRegistrationId();  //현재 로그인 중인 서비스를 구분하는 코드. 현재는 구글만 사용. 네이버와 구분 시 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();  // OAuth2 로그인시 PK로 삼을 필드 값. 구글의 기본코드 : 'sub', 네이버,카카오는 미지원.

        // OAuthAttributes : OAuth2Service를 통해 가져온 OAuth2User의 attribute를 담을 Dto 클래스.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); //OAuth2User에서 반환하는 사용자 정보는 Map이므로, 값 하나하나를 변환해야한다.

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));  // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto클래스
        /* User 클래스 그대로 사용시, 직렬화를 구현하지 않았으므로 오류 발생!. User Entity를 그대로 직렬화할 시 성능이나 부가적인 이슈가 발생할 수 있으므로 따로 SessionUser를 생성하여 사용*/

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRolekey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()) //기존 유저에서 email값으로 정보를 찾는다
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())) //정보가 있으면, 정보 업데이트
                .orElse(attributes.toEntity()); // 없으면, 유저 새로 생성

        return userRepository.save(user); //유저 정보 저장
    }

}
