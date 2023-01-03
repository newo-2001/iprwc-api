package me.new2001.webshop.products;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Optional;
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

    @NotNull
    private int price;

    public Product() {}

    public Product(String name, int price, String description, String thumbnailUri) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setThumbnailUri(thumbnailUri);
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

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
