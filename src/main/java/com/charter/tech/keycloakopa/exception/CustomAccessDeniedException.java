package com.charter.tech.keycloakopa.exception;

public class CustomAccessDeniedException extends RuntimeException {
    public CustomAccessDeniedException(String code) {
        super(code);
    }
}
