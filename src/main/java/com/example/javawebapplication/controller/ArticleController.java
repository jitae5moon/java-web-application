package com.example.javawebapplication.controller;

import com.example.javawebapplication.dto.ArticleDto;
import com.example.javawebapplication.dto.ArticlesWithCommentsCountDto;
import com.example.javawebapplication.dto.PageRequestDto;
import com.example.javawebapplication.dto.PageResponseDto;
import com.example.javawebapplication.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String getArticles(PageRequestDto pageRequestDto, Model model) {
        log.info("ArticleController :: getArticles :: pageRequestDto = {}", pageRequestDto);

        PageResponseDto<ArticlesWithCommentsCountDto> responseDto = articleService.getArticlesWithCommentsCount(pageRequestDto);

        model.addAttribute("responseDto", responseDto);

        return "articles/list";
    }

    @GetMapping("/detail")
    public String getArticle(Long id, PageRequestDto pageRequestDto, Model model) {
        log.info("ArticleController :: getArticle :: id = {}", id);

        ArticleDto article = articleService.getArticle(id);
        model.addAttribute("article", article);

        return "articles/detail";
    }

    @GetMapping("/update")
    public String updateArticle(Long id, PageRequestDto pageRequestDto, Model model) {
        log.info("ArticleController :: updateArticle :: id = {}", id);

        ArticleDto article = articleService.getArticle(id);
        model.addAttribute("article", article);

        return "articles/update";
    }

    @PostMapping("/update")
    public String updateArticle(@Valid ArticleDto articleDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, PageRequestDto pageRequestDto) {
        log.info("ArticleController :: updateArticle :: articleDto = {}", articleDto);

        if (bindingResult.hasErrors()) {
            log.info("ArticleController :: updateArticle :: bindingResult = {}", bindingResult);
            String link = pageRequestDto.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("id", articleDto.getId());

            return "redirect:/articles/update?" + link;
        }

        articleService.update(articleDto);

        redirectAttributes.addFlashAttribute("result", "Updated Successfully");
        redirectAttributes.addFlashAttribute("id", articleDto.getId());

        return "redirect:/articles/detail?id=" + articleDto.getId();
    }

    @GetMapping("/save")
    public String save(Model model) {

        return "articles/save";
    }

    @PostMapping("/save")
    public String save(@Valid ArticleDto articleDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("ArticleController :: save :: articleDto = {}", articleDto);

        if (bindingResult.hasErrors()) {
            log.error("ArticleController :: save :: errors = {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors()); // FlashAttributes are not included in query string

            return "redirect:/articles/save";
        }
        Long savedId = articleService.save(articleDto);

        redirectAttributes.addFlashAttribute("id", savedId);

        return "redirect:/articles";
    }

    @PostMapping("/delete")
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        log.info("ArticleController :: delete :: id = {}", id);

        articleService.delete(id);
        redirectAttributes.addFlashAttribute("result", "Deleted Successfully");

        return "redirect:/articles";
    }

}
