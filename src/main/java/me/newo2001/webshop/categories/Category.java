package me.newo2001.webshop.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.newo2001.webshop.products.Product;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Embeddable
@Entity
public class Category {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(unique = true)
    private String name;

    public Category() {}

    public Category(String category) {
        setName(category);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name must contain at least one character");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Category)) return false;
        return ((Category) other).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
