package com.csb.springbook.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 배포 시 어떤 port를 쓸 지 판단하는 기준이 되는 API
 */
@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {

        List<String> profiles = Arrays.asList(env.getActiveProfiles());  // 현재 실행 중인 ActiveProfile을 모두 가져온다.
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        /* 활성화된 profile을 모두 가져와서, 배포에 사용될 profile 목록 중 하나라도 있으면 그 값을 반환하고, 아니면 default를 반환. */
        return profiles.stream()
                .filter(o -> realProfiles.contains(o)) // realProfiles에 해당하는 값을 찾음. 있으면 그 값 반환.
                .findAny() //위에서 조건에 걸린 값이 없으면, profiles 의 첫번째 값 반환
                .orElse(defaultProfile); //아무 값도 없으면 defaultProfile 반환
    }
}
