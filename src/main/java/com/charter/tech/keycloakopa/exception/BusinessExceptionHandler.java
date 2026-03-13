package com.charter.tech.keycloakopa.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Exception handler for resource not found scenarios.
 * This runtime exception is thrown when a requested resource cannot be found in the system.
 */
public class BusinessExceptionHandler extends RuntimeException {
    @Getter
    @Setter
    private String code;

    /**
     * Constructs a new BusinessExceptionHandler with the specified error message.
     *
     * @param msg the detail message explaining the not found condition
     */
    public BusinessExceptionHandler(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
