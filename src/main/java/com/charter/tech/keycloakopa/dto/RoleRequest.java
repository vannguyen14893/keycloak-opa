package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;

@ApiModule("ROLE")
public record RoleRequest(@NotBlank(message = "{code.required}") String code,
                          @NotBlank(message = "{name.required}") String name) {
}
