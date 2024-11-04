package com.example.javawebapplication.service;

import com.example.javawebapplication.dto.CommentDto;
import com.example.javawebapplication.dto.PageRequestDto;
import com.example.javawebapplication.dto.PageResponseDto;

public interface CommentService {

    CommentDto getComment(Long id);

    Long save(CommentDto commentDto);

    void update(CommentDto commentDto);

    void delete(Long id);

    PageResponseDto<CommentDto> getCommentsOfArticle(Long articleId, PageRequestDto pageRequestDto);

}
