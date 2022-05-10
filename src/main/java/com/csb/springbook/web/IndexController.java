package com.csb.springbook.web;

import com.csb.springbook.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc()); //post라는 껍데기로 findAllDesc의 결과값을 감싸서 넘긴다.
        return "index";  //머스타치 스타터가 앞의 경로와 뒤 확장자를 자동으로 붙여준다.
                        // src/main/resources/templates/index.mustache로 전환되어 viewResolver가 처리하게 된다.
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
