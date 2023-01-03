package me.new2001.webshop.products;

import me.new2001.webshop.common.pagination.Paginated;
import me.new2001.webshop.common.pagination.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found")));
    }

    @GetMapping
    public ResponseEntity<Paginated<Product>> getProduct(@RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "20") int pageSize) {

        return ResponseEntity.ok(productService.getProductPage(new PaginationRequest(page, pageSize)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto creationData) {
        return ResponseEntity.ok(productService.createProduct(creationData));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody CreateProductDto creationData) {
        return ResponseEntity.ok(productService.updateProduct(id, creationData));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
