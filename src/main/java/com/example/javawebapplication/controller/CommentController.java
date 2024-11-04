package com.example.javawebapplication.controller;

import com.example.javawebapplication.dto.CommentDto;
import com.example.javawebapplication.dto.PageRequestDto;
import com.example.javawebapplication.dto.PageResponseDto;
import com.example.javawebapplication.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "Comments of Article")
    @GetMapping("/list/{articleId}")
    public PageResponseDto<CommentDto> getComments(@PathVariable Long articleId, PageRequestDto pageRequestDto) {
        PageResponseDto<CommentDto> commentsOfArticle = commentService.getCommentsOfArticle(articleId, pageRequestDto);

        return commentsOfArticle;
    }

    @ApiOperation(value = "Read Comment")
    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id) {
        CommentDto comment = commentService.getComment(id);

        return comment;
    }

    @ApiOperation(value = "Comments POST")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> save(@RequestBody @Valid CommentDto commentDto, BindingResult bindingResult) throws BindException {
        log.info("CommentController :: save :: commentDto = {}", commentDto);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Long savedId = commentService.save(commentDto);

        Map<String, Long> result = new HashMap<>();
        result.put("id", savedId);

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "Update Comment")
    @PutMapping("/{id}")
    public Map<String, Long> update(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        log.info("CommentController :: update :: commentDto = {}", commentDto);

        commentDto.setId(id);

        commentService.update(commentDto);

        Map<String, Long> result = new HashMap<>();
        result.put("id", id);

        return result;
    }


    @ApiOperation(value = "Delete Comment")
    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable Long id) {
        log.info("CommentController :: delete :: id = {}", id);

        commentService.delete(id);

        Map<String, Long> result = new HashMap<>();
        result.put("id", id);

        return result;
    }

}
