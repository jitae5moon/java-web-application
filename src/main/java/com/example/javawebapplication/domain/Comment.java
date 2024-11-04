package com.example.javawebapplication.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Table(indexes = {
        @Index(name = "idx_comment_article_id", columnList = "article_id")
})
@Entity
public class Comment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    private String createdBy;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    public void changeContent(String content) {
        this.content = content;
    }

    protected Comment() { }

    public Comment(String content, String createdBy, Article article) {
        this.content = content;
        this.createdBy = createdBy;
        this.article = article;
    }

}
