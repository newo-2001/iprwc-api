package me.new2001.webshop.security;

import me.new2001.webshop.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            JWTUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public JwtResponseDto register(RegisterRequestDto credentials) {
        String encodedPassword = passwordEncoder.encode(credentials.password());
        userService.createUser(new RegisterRequestDto(credentials.email(), encodedPassword));

        return new JwtResponseDto(jwtUtil.generateToken(credentials.email()));
    }

    @Override
    public JwtResponseDto login(LoginRequestDto dto) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                dto.email(), dto.password());

        authenticationManager.authenticate(credentials);
        return new JwtResponseDto(jwtUtil.generateToken(dto.email()));
    }
}
