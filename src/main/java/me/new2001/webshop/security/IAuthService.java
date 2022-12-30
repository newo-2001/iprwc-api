package me.new2001.webshop.security;

import me.new2001.webshop.users.LoginRequestDto;
import me.new2001.webshop.users.RegisterRequestDto;

public interface IAuthService {
    JwtResponseDto register(RegisterRequestDto credentials);
    JwtResponseDto login(LoginRequestDto credentials);
}
