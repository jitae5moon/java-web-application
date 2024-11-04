package com.example.javawebapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @NotNull
    private Long articleId;

    @NotEmpty
    private String content;

    @NotEmpty
    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
