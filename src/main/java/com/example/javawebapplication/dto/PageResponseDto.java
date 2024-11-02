package com.example.javawebapplication.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDto<E> {

    private int page;
    private int size;
    private int total;

    private int start;
    private int end;

    private boolean hasPrev;
    private boolean hasNext;

    private List<E> dtoList;

    // TODO: Seperate logic to PaginationService
    @Builder(builderMethodName = "withAll")
    public PageResponseDto(PageRequestDto pageRequestDto, List<E> dtoList, int total) {
        if (total <= 0) return;

        this.page = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int) Math.ceil(this.page / 10.0) * 10;
        this.start = this.end - 9;

        int last = (int) Math.ceil((total / (double) size));

        this.end = end > last ? last : end;
        this.hasPrev = this.start > 1;
        this.hasNext = total > this.end * this.size;
    }

}
