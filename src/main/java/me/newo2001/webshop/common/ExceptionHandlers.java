package me.newo2001.webshop.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.newo2001.webshop.users.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandlers {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiError error = new ApiError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleJsonParseException() {
        ApiError error = new ApiError(HttpServletResponse.SC_BAD_REQUEST, "Malformed JSON");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleNotFoundException() {
        ApiError error = new ApiError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleAuthenticationException() {
        ApiError error = new ApiError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleAccessDeniedException() {
        ApiError error = new ApiError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleConflict(UserExistsException e) {
        ApiError error = new ApiError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException e) {
        ApiError error = new ApiError(e.getRawStatusCode(), e.getReason());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler({IllegalStateException.class, NullPointerException.class})
    @ResponseBody
    public ResponseEntity<ApiError> handleInternalServerError(Exception e) {
        LOGGER.error(String.format("[%s]: %s", e.getClass(), e.getMessage()));
        ApiError error = new ApiError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().get(0).getDefaultMessage();
        ApiError error = new ApiError(HttpServletResponse.SC_BAD_REQUEST, message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
