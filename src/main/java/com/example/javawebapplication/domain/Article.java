package com.example.javawebapplication.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.*;

@Getter
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 500, nullable = false)
    private String title;

    @Setter
    @Column(length = 2000, nullable = false)
    private String content;

    @Setter
    @Column(length = 50, nullable = false)
    private String createdBy;

    protected Article() { }

    public Article(Long id, String title, String content, String createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

}
