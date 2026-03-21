package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;

@ApiModule("SCOPES")
public record ScopesRequest(@NotBlank(message = "{name.required}") String name,
                            @NotBlank(message = "{description.required}") String description) {
}
