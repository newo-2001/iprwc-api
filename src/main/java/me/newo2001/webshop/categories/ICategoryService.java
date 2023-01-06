package me.newo2001.webshop.categories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryService {
    List<Category> getAll();
    Optional<Category> findCategoryById(UUID id);
    Optional<Category> findCategoryByName(String name);
    void deleteCategory(UUID id);
    Category createCategory(CreateCategoryDto category);
    Category updateCategory(UUID id, CreateCategoryDto category);
}
