package me.newo2001.webshop.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RegisterRequestDto(
        @NotNull(message="Email must not be null")
        @NotBlank(message="Email must not be blank")
        @Email(message="Email address is not valid")
        String email,
        @NotNull(message="Password must not be null")
        @NotBlank(message="Password must not be blank")
        @Size(min=8, max=250, message="Password must be between 8 and 250 characters")
        String password) {
}
