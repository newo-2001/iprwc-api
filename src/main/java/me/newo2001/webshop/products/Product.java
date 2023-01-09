package me.newo2001.webshop.products;

import com.sun.istack.NotNull;
import me.newo2001.webshop.categories.Category;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @NotNull
    private String name;
    private String description;
    private String thumbnailUri;

    @ManyToMany
    @JoinTable(name="product_category",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="categories_id"))
    private Set<Category> categories;

    @NotNull
    private int price;

    public Product() {}

    public Product(String name, int price, String description, String thumbnailUri, Set<Category> categories) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setThumbnailUri(thumbnailUri);
        setCategories(categories);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public int getPrice() {
        return price;
    }

    public Optional<String> getThumbnailUri() {
        return Optional.ofNullable(thumbnailUri);
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty");
        }

        this.name = name;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price can't be negative");
        }

        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
