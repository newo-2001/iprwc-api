package me.newo2001.webshop.products;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CreateProductDto(
        @NotBlank(message="Name must not be blank")
        @NotNull(message="Name must not be null")
        String name,
        String description,
        @NotNull(message="Price must not be null")
        @Min(value=0, message="Price must be non-negative")
        int price,
        String thumbnailUri,
        @NotNull(message = "Categories must not be null")
        UUID[] categories) {
}
