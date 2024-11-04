package com.example.javawebapplication.repository.querydsl;

import com.example.javawebapplication.domain.Article;
import com.example.javawebapplication.dto.ArticlesWithCommentsCountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleSearch {

    Page<Article> searchAll(String[] types, String keyword, Pageable pageable);

    Page<ArticlesWithCommentsCountDto> searchWithCommentCount(String[] types, String keyword, Pageable pageable);

}
