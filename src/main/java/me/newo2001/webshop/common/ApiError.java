package me.newo2001.webshop.common;

import javax.servlet.http.HttpServletResponse;

public record ApiError(int status, String reason) {
    public static ApiError fail(HttpServletResponse response, int status, String reason) {
        response.setStatus(status);
        return new ApiError(status, reason);
    }
}
