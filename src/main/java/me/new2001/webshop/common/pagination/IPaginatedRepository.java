package me.new2001.webshop.common.pagination;

public interface IPaginatedRepository<T> {
    Paginated<T> getPage(int page, int pageSize);
}
