package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "error_catalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String type;
    private Integer httpStatus;
    private String messageKey;
    private String service;
    private String severity;
    private Boolean retryable;
}
