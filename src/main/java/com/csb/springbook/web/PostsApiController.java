package com.csb.springbook.web;

import com.csb.springbook.service.posts.PostsService;
import com.csb.springbook.web.dto.PostResponseDto;
import com.csb.springbook.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    //    http://xxx.x.x?index=1&page=2 쿼리스트링의 값을 변수에 매핑할 때 : @RequestParam("index") String index
//    @PostMapping("delete/{idx}")  @PathVariable("idx") int id  REST Api 형식에서 구분자 값을 변수에 매핑할 때 : @PathVariable String
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsSaveRequestDto requestDto) {
        System.out.println("requestDto = " + requestDto);
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
