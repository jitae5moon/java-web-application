package com.example.javawebapplication.repository.querydsl;

import com.example.javawebapplication.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleSearch {

    Page<Article> searchAll(String[] types, String keyword, Pageable pageable);

}
