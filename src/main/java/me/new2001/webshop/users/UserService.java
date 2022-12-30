package me.new2001.webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email); }

    public User createUser(RegisterRequestDto dto) {
        User user = new User(dto.email(), dto.password());
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new UserExistsException();
        }

        userRepository.save(user);
        return user;
    }
}
