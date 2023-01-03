package me.new2001.webshop.users;

import javax.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
