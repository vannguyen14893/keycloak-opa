package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "network_configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkConfigs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long adapterId;
    private String environment;
    @Column(name = "connection_details", columnDefinition = "jsonb")
    private String connectionDetails;
    @Column(name = "security_configs", columnDefinition = "jsonb")
    private String securityConfigs;
    @Column(name = "resilience_configs", columnDefinition = "jsonb")
    private String resilienceConfigs;
    private String version;
}
