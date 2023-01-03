package me.new2001.webshop.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Not Found")
public class NotFoundException extends RuntimeException {
}
