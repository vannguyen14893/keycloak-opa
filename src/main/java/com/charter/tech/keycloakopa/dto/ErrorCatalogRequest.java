package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ApiModule("ERROR_CATALOG")
public record ErrorCatalogRequest(@NotBlank(message = "{code.required}") String code,
                                  @NotBlank(message = "{type.required}") String type,
                                  @NotNull(message = "{httpStatus.required}") Integer httpStatus,
                                  @NotBlank(message = "{messageKey.required}") String messageKey,
                                  @NotBlank(message = "{service.required}") String service,
                                  @NotBlank(message = "{severity.required}") String severity,
                                  Boolean retryable) {
}
