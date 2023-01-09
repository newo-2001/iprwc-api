package me.newo2001.webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("User authenticated without account"));

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new UserResponseDto(authenticatedUser.getId(), authenticatedUser.getEmail(), roles));
    }
}
