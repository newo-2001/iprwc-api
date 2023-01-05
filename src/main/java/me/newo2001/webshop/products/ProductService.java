package me.newo2001.webshop.products;

import me.newo2001.webshop.common.NotFoundException;
import me.newo2001.webshop.common.pagination.Paginated;
import me.newo2001.webshop.common.pagination.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;

    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public Product createProduct(CreateProductDto dto) {
        Product product = new Product(dto.name(), dto.price(), dto.description(), dto.thumbnailUri());
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

        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                        .orElseThrow(NotFoundException::new);
        productRepository.delete(product);
    }
}
