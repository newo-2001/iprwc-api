package me.newo2001.webshop.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :category")
    Page<Product> findAllByCategory(UUID category, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :category")
    List<Product> findAllByCategory(UUID category);
}
