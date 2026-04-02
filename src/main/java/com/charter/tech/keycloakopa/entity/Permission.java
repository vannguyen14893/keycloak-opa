package com.charter.tech.keycloakopa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = "code")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "permissions_copes",
            joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "scopes_id")
    )
    private Set<Scopes> scopes = new HashSet<>();
}
