package com.charter.tech.keycloakopa.dto;

public record ErrorCatalogResponse(Long id,
                                   String code,
                                   String type,
                                   Integer httpStatus,
                                   String messageKey,
                                   String service,
                                   String severity,
                                   Boolean retryable) {
}
