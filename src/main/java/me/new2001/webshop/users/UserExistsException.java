package me.new2001.webshop.users;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User with that email address already exists");
    }
}
