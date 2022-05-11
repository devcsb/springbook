package com.csb.springbook.domain.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 권한을 관리할 Enum 클래스
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),  /*스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 붙어있어야 한다.*/
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
