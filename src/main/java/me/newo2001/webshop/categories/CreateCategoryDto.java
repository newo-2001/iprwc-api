package me.newo2001.webshop.categories;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateCategoryDto(
        @NotNull(message="Name must not be null")
        @NotEmpty(message="Name must not be empty")
        String name) {
}
