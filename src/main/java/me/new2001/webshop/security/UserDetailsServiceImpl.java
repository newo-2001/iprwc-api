package me.new2001.webshop.security;

import me.new2001.webshop.users.IUserRepository;
import me.new2001.webshop.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final List<String> ADMINS = Collections.singletonList("owen@gmail.com");
    private final IUserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("Could not find user with email " + email);

        List<SimpleGrantedAuthority> roles = new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        if (ADMINS.contains(email)) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new org.springframework.security.core.userdetails.User(
                email,
                user.get().getPassword(),
                roles
        );
    }
}
