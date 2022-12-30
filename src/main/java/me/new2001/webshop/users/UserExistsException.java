package me.new2001.webshop.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="User with that email already exists")
public class UserExistsException extends RuntimeException {
}
