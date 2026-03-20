package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;
@ApiModule("LANGUAGES")
public record LanguagesRequest(@NotBlank(message = "{code.required") String code,
                               @NotBlank(message = "{name.required") String name, Boolean status) {
}
