package me.newo2001.webshop.products;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

public record CreateProductDto(
        @NotBlank
        String name,
        String description,
        @Min(0)
        int price,
        String thumbnailUri,
        UUID[] categories) {
}
