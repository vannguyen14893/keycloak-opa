package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;

@ApiModule("PII_RULE")
public record PiiRuleRequest(@NotBlank(message = "{key.required}") String key,
                             @NotBlank(message = "{masked.required}") String masked,
                             Boolean status) {
}
