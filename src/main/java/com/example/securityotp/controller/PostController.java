package com.example.securityotp.controller;

import com.example.securityotp.dto.PostDto;
import com.example.securityotp.dto.post.PostSearchDto;
import com.example.securityotp.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public String showListPage(@ModelAttribute("condition") PostSearchDto condition, Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<PostDto> posts = postService.findAllPosts(condition, pageable);

        model.addAttribute("posts", posts);

        log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                posts.getTotalElements(), posts.getTotalPages(), posts.getSize(), posts.getNumber(), posts.getNumberOfElements());

        return "post/list";
    }

    @GetMapping("/posts/{id}")
    public String showDetailPage(@PathVariable("id") String id, Model model) {
        PostDto post = postService.findPost(Long.valueOf(id));

        model.addAttribute("post", post);

        return "post/detail";
    }

    @GetMapping("/posts/edit/{id}")
    public String showEditPage(@PathVariable("id") String id, Model model) {
        PostDto post = postService.findPost(Long.valueOf(id));

        model.addAttribute("post", post);

        return "post/edit";
    }

    @PostMapping("/posts/edit/{id}")
    public String editAndCreate(@PathVariable("id") String id,
                                @Validated @ModelAttribute("post") PostDto postDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/edit";
        }

        PostDto post = postService.editAndCreate(postDto);

        return "redirect:/posts/" + post.getId();
    }

//    @PostMapping("/data")
//    public String dataSend(Model model, @RequestBody int page){
//        Pageable pageable = PageRequest.of(page, 10);
//        Page<PostDto> posts = postService.findAllPosts(pageable);
//
//        model.addAttribute("posts", posts);
//
//        return "post/list :: #post-table";
//    }
}
