package me.newo2001.webshop.security;

import me.newo2001.webshop.users.UserResponseDto;

public record UserInfoDto(String token, UserResponseDto user) {
}
