package me.newo2001.webshop.products;

import me.newo2001.webshop.categories.Category;
import me.newo2001.webshop.categories.ICategoryRepository;
import me.newo2001.webshop.common.NotFoundException;
import me.newo2001.webshop.common.pagination.Paginated;
import me.newo2001.webshop.common.pagination.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    @Autowired
    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Product> findProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Paginated<Product> getProductPage(PaginationRequest page) {
        return Paginated.fromPage(productRepository.findAll(page.asPageRequest(Sort.unsorted())));
    }

    @Override
    public Paginated<Product> getProductPageByCategory(Category category, PaginationRequest page) {
        return Paginated.fromPage(productRepository.findAllByCategory(category.getId(), page.asPageRequest(Sort.unsorted())));
    }

    @Override
    public Product createProduct(CreateProductDto dto) {
        Set<Category> categories = getAllCategories(dto.categories());
        Product product = new Product(dto.name(), dto.price(), dto.description(), dto.thumbnailUri(), categories);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(UUID id, CreateProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        product.setThumbnailUri(dto.thumbnailUri());
        product.setDescription(dto.description());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setCategories(getAllCategories(dto.categories()));

        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                        .orElseThrow(NotFoundException::new);
        productRepository.delete(product);
    }

    private Set<Category> getAllCategories(UUID[] categoryIds) {
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(List.of(categoryIds)));

        if (!Arrays.stream(categoryIds).allMatch(x -> categories.stream().anyMatch(y -> y.getId().equals(x)))) {
            throw new IllegalArgumentException("Categories contains invalid category id");
        }

        return categories;
    }
}
