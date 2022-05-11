package com.csb.springbook.web;

import com.csb.springbook.config.auth.LoginUser;
import com.csb.springbook.config.auth.dto.SessionUser;
import com.csb.springbook.service.posts.PostsService;
import com.csb.springbook.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc()); //post라는 껍데기로 findAllDesc의 결과값을 감싸서 넘긴다.

//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); //세션에서 유저 정보 가져오기 // @LoginUser 커스텀 어노테이션으로 변경

        if (user != null) {  //세션에 저장된 값이 있을 때만 model에 userName으로 등록
            model.addAttribute("loginUserName", user.getName());
        }

        return "index";  //머스타치 스타터가 앞의 경로와 뒤 확장자를 자동으로 붙여준다.
        // src/main/resources/templates/index.mustache로 전환되어 viewResolver가 처리하게 된다.
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
