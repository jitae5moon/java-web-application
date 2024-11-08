package com.example.javawebapplication.repository;

import com.example.javawebapplication.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.article.id = :id")
    Page<Comment> getCommentsByArticleId(Long id, Pageable pageable);

}
