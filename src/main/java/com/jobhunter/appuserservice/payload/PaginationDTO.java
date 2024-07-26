package com.jobhunter.appuserservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Pagination information along with the paginated content")
public class PaginationDTO<T> {

    @Schema(description = "Total number of pages available", example = "5")
    private int totalPages;

    @Schema(description = "Current page number", example = "1")
    private int page;

    @Schema(description = "Number of items per page", example = "20")
    private int size;

    @Schema(description = "Total number of elements across all pages", example = "100")
    private long totalElements;

    @Schema(description = "List of items on the current page")
    private List<T> content;

    @Schema(description = "Whether there is a previous page available", example = "true")
    private boolean hasPrevious;

    @Schema(description = "Whether there is a next page available", example = "true")
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
