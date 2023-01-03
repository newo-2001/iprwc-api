package me.new2001.webshop.security;

import me.new2001.webshop.users.User;
import me.new2001.webshop.users.UserResponseDto;

public record UserInfoDto(String token, UserResponseDto user) {
}
