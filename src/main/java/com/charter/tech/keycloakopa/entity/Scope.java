package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        name = "scopes",
        uniqueConstraints = @UniqueConstraint(columnNames = "code")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String code;
    // own, branch, org, any, amount, time
    private String attribute;

    private String description;

    // owner_id, branch_id, amount, transaction_time
}

