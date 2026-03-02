package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.PermissionScopes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionScopeRepository extends JpaRepository<PermissionScopes, Long> {
    List<PermissionScopes> findPermissionScopesByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
