package com.example.javawebapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ArticlesWithCommentsCountDto {

    private Long id;
    private String title;
    private String createdBy;
    private LocalDateTime createdAt;

    private Long commentCount;

}
