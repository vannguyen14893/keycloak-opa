package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByCode(String code);
}
