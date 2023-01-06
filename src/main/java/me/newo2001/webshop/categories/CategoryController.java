package me.newo2001.webshop.categories;

import me.newo2001.webshop.common.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id).orElseThrow(NotFoundException::new));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody CreateCategoryDto dto) {
        categoryService.findCategoryByName(dto.name()).ifPresent(category -> {
            if (!category.getId().equals(id))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with that name already exists");
        });

        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryDto dto) {
        categoryService.findCategoryByName(dto.name()).ifPresent(x -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with that name already exists");
        });

        return ResponseEntity.ok(categoryService.createCategory(dto));
    }
}
