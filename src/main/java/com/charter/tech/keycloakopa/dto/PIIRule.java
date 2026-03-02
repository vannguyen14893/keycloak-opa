package com.charter.tech.keycloakopa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PIIRule {
    private String key;
    private String masked;
}
