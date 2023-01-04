package me.newo2001.webshop.users;

import java.util.List;
import java.util.UUID;

public record UserResponseDto(UUID id, String email, List<String> roles) {
}
