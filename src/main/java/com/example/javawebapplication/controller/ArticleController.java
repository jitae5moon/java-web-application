package com.example.javawebapplication.controller;

import com.example.javawebapplication.dto.ArticleDto;
import com.example.javawebapplication.dto.PageRequestDto;
import com.example.javawebapplication.dto.PageResponseDto;
import com.example.javawebapplication.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String getArticles(PageRequestDto pageRequestDto, Model model) {
        log.info("ArticleController :: getArticles :: pageRequestDto = {}", pageRequestDto);

        PageResponseDto<ArticleDto> responseDto = articleService.getArticles(pageRequestDto);

        model.addAttribute("responseDto", responseDto);

        return "articles/list";
    }

}
