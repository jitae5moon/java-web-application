package com.example.javawebapplication.repository;

import com.example.javawebapplication.domain.Article;
import com.example.javawebapplication.repository.querydsl.ArticleSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleSearch {
}
