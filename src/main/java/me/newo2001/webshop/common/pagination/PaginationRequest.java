package me.newo2001.webshop.common.pagination;

import org.springframework.data.domain.PageRequest;

public record PaginationRequest(int page, int pageSize) {
    public PaginationRequest {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size has to be positive");
        } else if (pageSize > Paginated.MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Maximum page size is " + Paginated.MAX_PAGE_SIZE);
        } else if (page <= 0) {
            throw new IllegalArgumentException("Page has to be positive");
        }
    }

    public PageRequest asPageRequest() {
        return PageRequest.of(page-1, pageSize);
    }
}
