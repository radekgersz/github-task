package org.example.app;

public record ApiErrorResponse(
        int status,
        String message
) {
}
