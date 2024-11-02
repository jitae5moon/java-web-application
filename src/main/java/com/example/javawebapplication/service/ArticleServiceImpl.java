package com.example.javawebapplication.service;

import com.example.javawebapplication.domain.Article;
import com.example.javawebapplication.dto.ArticleDto;
import com.example.javawebapplication.dto.PageRequestDto;
import com.example.javawebapplication.dto.PageResponseDto;
import com.example.javawebapplication.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDto<ArticleDto> getArticles(PageRequestDto pageRequestDto) {
        String[] types = pageRequestDto.getTypes();
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable("id");

        Page<Article> result = articleRepository.searchAll(types, keyword, pageable);

        List<ArticleDto> dtoList = result.getContent().stream()
                .map(article -> modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());

        return PageResponseDto.<ArticleDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public ArticleDto getArticle(Long id) {
        Optional<Article> result = articleRepository.findById(id);
        Article article = result.orElseThrow();

        return modelMapper.map(article, ArticleDto.class);
    }

    @Override
    public Long save(ArticleDto articleDto) {
        Article article = modelMapper.map(articleDto, Article.class);

        return articleRepository.save(article).getId();
    }

    @Override
    public void update(ArticleDto articleDto) {
        Optional<Article> result = articleRepository.findById(articleDto.getId());

        Article article = result.orElseThrow();

        article.change(articleDto.getTitle(), articleDto.getContent());
        articleRepository.save(article);
    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
