package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
