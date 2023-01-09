package me.newo2001.webshop.categories;

import me.newo2001.webshop.common.NotFoundException;
import me.newo2001.webshop.products.IProductRepository;
import me.newo2001.webshop.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository, IProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Category> findCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = findCategoryById(id)
                .orElseThrow(NotFoundException::new);

        List<Product> products = productRepository.findAllByCategory(id);
        for (Product product : products) {
            Set<Category> categories = product.getCategories();
            categories.remove(category);
        }

        productRepository.saveAll(products);
        categoryRepository.delete(category);
    }

    @Override
    public Category createCategory(CreateCategoryDto dto) {
        return categoryRepository.save(new Category(dto.name()));
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category updateCategory(UUID id, CreateCategoryDto dto) {
        Category category = findCategoryById(id)
                .orElseThrow(NotFoundException::new);
        category.setName(dto.name());

        return categoryRepository.save(category);
    }
}
