package com.example.javawebapplication.repository.querydsl;

import com.example.javawebapplication.domain.Article;
import com.example.javawebapplication.domain.QArticle;
import com.example.javawebapplication.domain.QComment;
import com.example.javawebapplication.dto.ArticlesWithCommentsCountDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleSearchImpl extends QuerydslRepositorySupport implements ArticleSearch {

    public ArticleSearchImpl() {
        super(Article.class);
    }

    // TODO: Change types to enum
    @Override
    public Page<Article> searchAll(String[] types, String keyword, Pageable pageable) {
        QArticle article = QArticle.article;
        JPQLQuery<Article> query = from(article);

        if (types != null && types.length > 0 && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(article.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(article.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(article.createdBy.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        query.where(article.id.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Article> articles = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(articles, pageable, count);
    }

    @Override
    public Page<ArticlesWithCommentsCountDto> searchWithCommentCount(String[] types, String keyword, Pageable pageable) {
        QArticle article = QArticle.article;
        QComment comment = QComment.comment;
        JPQLQuery<Article> query = from(article);

        query.leftJoin(comment).on(comment.article.eq(article));
        query.groupBy(article);

        if (types != null && types.length > 0 && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(article.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(article.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(article.createdBy.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        query.where(article.id.gt(0L));

        JPQLQuery<ArticlesWithCommentsCountDto> dtoJPQLQuery = query.select(
                Projections.bean(ArticlesWithCommentsCountDto.class,
                        article.id,
                        article.title,
                        article.createdBy,
                        article.createdAt,
                        comment.count().as("commentCount")));

        this.getQuerydsl().applyPagination(pageable, dtoJPQLQuery);

        List<ArticlesWithCommentsCountDto> dtoList = dtoJPQLQuery.fetch();
        long count = dtoJPQLQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }

}
