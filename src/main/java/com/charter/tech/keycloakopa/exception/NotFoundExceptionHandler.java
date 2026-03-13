package com.charter.tech.keycloakopa.exception;

/**
 * Exception handler for resource not found scenarios.
 * This runtime exception is thrown when a requested resource cannot be found in the system.
 */
public class NotFoundExceptionHandler extends RuntimeException {

    /**
     * Constructs a new NotFoundExceptionHandler with the specified error message.
     *
     * @param msg the detail message explaining the not found condition
     */
    public NotFoundExceptionHandler(String msg) {
        super(msg);
    }
}
