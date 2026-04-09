package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "network_adapters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkAdapters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String adapterCode;
    private String protocolType;
    private String description;
    private boolean is_active;
}
