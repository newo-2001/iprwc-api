package me.new2001.webshop.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.new2001.webshop.users.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

import static me.new2001.webshop.common.ApiError.fail;

@ControllerAdvice
public class ExceptionHandlers {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ApiError handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {
        return fail(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    public ApiError handleJsonParseException(JsonProcessingException e, HttpServletResponse response) {
        return fail(response, HttpServletResponse.SC_BAD_REQUEST, "Malformed JSON");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ApiError handleNotFoundException(NotFoundException e, HttpServletResponse response) {
        return fail(response, HttpServletResponse.SC_NOT_FOUND, "Not Found");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ApiError handleAuthenticationException(AuthenticationException e, HttpServletResponse response) {
        return fail(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ApiError handleAccessDeniedException(AccessDeniedException e, HttpServletResponse response) {
        return fail(response, HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    public ApiError handleConflict(UserExistsException e, HttpServletResponse response) {
        return fail(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ApiError handleResponseStatusException(ResponseStatusException e, HttpServletResponse response) {
        return fail(response, e.getRawStatusCode(), e.getReason());
    }

    @ExceptionHandler({IllegalStateException.class, NullPointerException.class})
    @ResponseBody
    public ApiError handleInternalServerError(Exception e, HttpServletResponse response) {
        LOGGER.error(String.format("[%s]: %s", e.getClass(), e.getMessage()));
        return fail(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}
