package com.example.javawebapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    private String title;

    private String content;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
