package me.newo2001.webshop.security;

import me.newo2001.webshop.users.LoginRequestDto;
import me.newo2001.webshop.users.RegisterRequestDto;

public interface IAuthService {
    UserInfoDto register(RegisterRequestDto credentials);
    UserInfoDto login(LoginRequestDto credentials);
}
