package com.charter.tech.keycloakopa.dto;

public record PiiRuleResponse(Long id,
                              String key,
                              String masked,
                              Boolean status) {
}
