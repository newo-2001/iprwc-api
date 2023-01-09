package me.newo2001.webshop.products;

import me.newo2001.webshop.categories.Category;
import me.newo2001.webshop.categories.ICategoryService;
import me.newo2001.webshop.common.NotFoundException;
import me.newo2001.webshop.common.pagination.Paginated;
import me.newo2001.webshop.common.pagination.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final ICategoryService categoryService;

    @Autowired
    public ProductController(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found")));
    }

    @GetMapping
    public ResponseEntity<Paginated<Product>> getProductPage(@RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "20") int pageSize,
                                                             @RequestParam(required = false) UUID categoryId) {
        PaginationRequest pageRequest = new PaginationRequest(page, pageSize);
        if (categoryId == null) {
            return ResponseEntity.ok(productService.getProductPage(pageRequest));
        }

        Category category = categoryService.findCategoryById(categoryId).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(productService.getProductPageByCategory(category, pageRequest));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductDto creationData) {
        if (Arrays.stream(creationData.categories()).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Category id must not be null");
        }

        return ResponseEntity.ok(productService.createProduct(creationData));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody @Valid CreateProductDto creationData) {
        if (Arrays.stream(creationData.categories()).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Category id must not be null");
        }

        return ResponseEntity.ok(productService.updateProduct(id, creationData));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
