package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permission_scopes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionScopes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roleId;
    private Long permissionId;
    private Long scopeId;
    private String scopeValue;
}
