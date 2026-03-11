package com.charter.tech.keycloakopa.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard success response wrapper")
public record ResultResponse<T>(
        @Schema(description = "Unique identifier for the response", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "HTTP status code", example = "200")
        String status,

        @Schema(description = "Response message", example = "Operation completed successfully")
        String message,

        @Schema(description = "Response payload data")
        T data
) {}

