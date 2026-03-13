package com.charter.tech.keycloakopa.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard error response wrapper")
public record ErrorResultResponse<T>(
        @Schema(description = "Unique identifier for the response", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "Response code", example = "200")
        String code,

        @Schema(description = "Response message", example = "Operation completed successfully")
        String message,

        @Schema(description = "Severity level")
        String severity,

        @Schema(description = "Retryable flag")
        Boolean retryable,
        @Schema(description = "Response payload error details")
        T details
) {
}

