package me.newo2001.webshop.products;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record CreateProductDto(
        @NotBlank
        String name,
        String description,
        @Min(0)
        int price,
        String thumbnailUri) {
}
