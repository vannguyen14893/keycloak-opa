package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;

@ApiModule("USER")
public record UserDto(@NotBlank(message = "{username.required}") String username,
                      @NotBlank(message = "{password.required}") String password) {
}
