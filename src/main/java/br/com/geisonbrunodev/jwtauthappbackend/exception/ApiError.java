package br.com.geisonbrunodev.jwtauthappbackend.exception;

import java.time.Instant;
import java.util.UUID;

public record ApiError(
        String error,
        String message,
        String traceId,
        Instant timestamp
) {
    public static ApiError of(String error, String message) {
        return new ApiError(error, message, UUID.randomUUID().toString(), Instant.now());
    }
}
