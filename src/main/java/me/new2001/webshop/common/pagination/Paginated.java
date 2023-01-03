package me.new2001.webshop.common.pagination;

import org.springframework.data.domain.Page;

import java.util.List;

public record Paginated<T>(List<T> items, int page, int pageSize, int totalPages) {
    public static final int MAX_PAGE_SIZE = 50;

    public static <T> Paginated<T> fromPage(Page<T> page) {
        return new Paginated<T>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalPages());
    }
}
