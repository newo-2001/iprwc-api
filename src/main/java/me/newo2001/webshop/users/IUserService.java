package me.newo2001.webshop.users;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByEmail(String email);
    User createUser(RegisterRequestDto dto);
}
