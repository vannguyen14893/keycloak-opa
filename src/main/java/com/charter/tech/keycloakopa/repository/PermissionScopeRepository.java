package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.Scopes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionScopeRepository extends JpaRepository<Scopes, Long> {
    List<Scopes> findPermissionScopesByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
