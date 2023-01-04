package me.newo2001.webshop.security;

import me.new2001.webshop.users.*;
import me.newo2001.webshop.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public UserInfoDto register(RegisterRequestDto credentials) {
        String encodedPassword = passwordEncoder.encode(credentials.password());
        User user = userService.createUser(new RegisterRequestDto(credentials.email(), encodedPassword));

        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(
                credentials.email(), credentials.password());
        Authentication authentication = authenticationManager.authenticate(creds);

        String token = jwtUtil.generateToken(credentials.email());
        List<String> roles = getRoles(authentication);
        return new UserInfoDto(token, new UserResponseDto(user.getId(), user.getEmail(), roles));
    }

    @Override
    public UserInfoDto login(LoginRequestDto dto) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                dto.email(), dto.password());

        Authentication authentication = authenticationManager.authenticate(credentials);
        User user = userService.getUserByEmail(dto.email())
                .orElseThrow(() -> new IllegalStateException("User authenticated without account"));

        String token = jwtUtil.generateToken(dto.email());
        List<String> roles = getRoles(authentication);
        return new UserInfoDto(token, new UserResponseDto(user.getId(), user.getEmail(), roles));
    }

    private List<String> getRoles(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
    }
}
