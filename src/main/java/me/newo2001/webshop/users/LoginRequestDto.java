package me.newo2001.webshop.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotNull(message="Email must not be null")
        @NotBlank(message="Email must not be blank")
        String email,
        @NotNull(message="Password must not be blank")
        @NotBlank(message="Password must not be blank")
        String password) {
}
