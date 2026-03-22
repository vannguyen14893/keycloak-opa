package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.code = :code")
    List<Role> findByCode(String name);
}
