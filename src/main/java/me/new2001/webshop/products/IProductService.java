package me.new2001.webshop.products;

import me.new2001.webshop.common.pagination.Paginated;
import me.new2001.webshop.common.pagination.PaginationRequest;

import java.util.Optional;
import java.util.UUID;

public interface IProductService {
    Optional<Product> findProductById(UUID id);
    Paginated<Product> getProductPage(PaginationRequest page);
    Product createProduct(CreateProductDto dto);
    Product updateProduct(UUID id, CreateProductDto dto);
    void deleteProduct(UUID id);
}
