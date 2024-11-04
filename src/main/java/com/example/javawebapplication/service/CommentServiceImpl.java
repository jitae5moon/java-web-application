package com.example.javawebapplication.service;

import com.example.javawebapplication.domain.Comment;
import com.example.javawebapplication.dto.CommentDto;
import com.example.javawebapplication.dto.PageRequestDto;
import com.example.javawebapplication.dto.PageResponseDto;
import com.example.javawebapplication.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentDto getComment(Long id) {
        Optional<Comment> result = commentRepository.findById(id);

        Comment comment = result.orElseThrow();

        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public Long save(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        return commentRepository.save(comment).getId();
    }

    @Override
    public void update(CommentDto commentDto) {
        Optional<Comment> result = commentRepository.findById(commentDto.getId());

        Comment comment = result.orElseThrow();

        comment.changeContent(commentDto.getContent());

        commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public PageResponseDto<CommentDto> getCommentsOfArticle(Long articleId, PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() <= 0 ? 0 : pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("id").ascending());

        Page<Comment> result = commentRepository.getCommentsByArticleId(articleId, pageable);

        List<CommentDto> dtoList = result.getContent().stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());

        return PageResponseDto.<CommentDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

}
