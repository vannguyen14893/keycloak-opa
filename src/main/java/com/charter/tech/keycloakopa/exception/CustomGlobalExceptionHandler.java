package com.charter.tech.keycloakopa.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler {
    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(CustomAccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "error", "access_denied",
                        "message", ex.getMessage()

                ));
    }
}
