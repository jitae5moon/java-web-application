package com.example.javawebapplication.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
