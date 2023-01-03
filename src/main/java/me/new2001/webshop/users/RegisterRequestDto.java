package me.new2001.webshop.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min=5, max=250)
        String password) {
}
