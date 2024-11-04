package com.example.javawebapplication.service;

import com.example.javawebapplication.dto.ArticleDto;
import com.example.javawebapplication.dto.ArticlesWithCommentsCountDto;
import com.example.javawebapplication.dto.PageRequestDto;
import com.example.javawebapplication.dto.PageResponseDto;

public interface ArticleService {

    PageResponseDto<ArticleDto> getArticles(PageRequestDto pageRequestDto);

    ArticleDto getArticle(Long id);

    PageResponseDto<ArticlesWithCommentsCountDto> getArticlesWithCommentsCount(PageRequestDto pageRequestDto);

    Long save(ArticleDto articleDto);

    void update(ArticleDto articleDto);

    void delete(Long id);

}
