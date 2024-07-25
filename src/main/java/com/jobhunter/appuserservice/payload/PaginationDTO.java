package com.jobhunter.appuserservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationDTO<T> {

    private int totalPages;

    private int page;

    private int size;

    private long totalElements;

    private List<T> content;

    private boolean hasPrevious;

    private boolean hasNext;

    private PaginationDTO(int page, int size, List<T> content, boolean hasPrevious, boolean hasNext) {
        this.page = page;
        this.size = size;
        this.content = content;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
    }

    private PaginationDTO(int pageCount, int page, int size, long count, List<T> content) {
        this.totalPages = pageCount;
        this.page = page;
        this.size = size;
        this.totalElements = count;
        this.content = content;
    }

    public static <E> PaginationDTO<E> makeForSlice(int page, int size, List<E> content, boolean hasPrevious, boolean hasNext) {
        return new PaginationDTO<>(page, size, content, hasPrevious, hasNext);
    }

    public static <E> PaginationDTO<E> makeForPage(int totalPages, int page, int size, long totalElements, List<E> content) {
        return new PaginationDTO<>(totalPages, page, size, totalElements, content);
    }
}
