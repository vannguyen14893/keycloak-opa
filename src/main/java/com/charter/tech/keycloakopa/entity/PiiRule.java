package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pii_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PiiRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String masked;
    private Boolean status;

    public PiiRule(String key, String masked, Boolean status) {
        this.key = key;
        this.masked = masked;
        this.status = status;
    }
}
