package com.dbtraining.reconx.dto;

import org.springframework.data.domain.Page;
import java.util.List;
import java.util.function.Function;

public record PagedResponse<T>(
    List<T> items,
    int page,
    int size,
    long totalElements,
    int totalPages
) {
    public static <E, T> PagedResponse<T> of(Page<E> springPage, Function<E, T> mapper) {
        List<T> mappedItems = springPage.getContent().stream()
                .map(mapper)
                .toList();

        return new PagedResponse<>(
                mappedItems,
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements(),
                springPage.getTotalPages()
        );
    }
}