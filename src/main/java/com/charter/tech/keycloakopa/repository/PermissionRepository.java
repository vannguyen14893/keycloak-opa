package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
