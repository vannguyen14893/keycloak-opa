package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;

@ApiModule("SWAGGER_TRANSLATION")
public record SwaggerTranslationRequest(@NotBlank(message = "{key.required}") String key,
                                        @NotBlank(message = "{locale.required}") String locale,
                                        @NotBlank(message = "{name.required}") String name,
                                        @NotBlank(message = "{description.required}") String description) {
}
