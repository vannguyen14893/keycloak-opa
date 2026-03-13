package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface ErrorCatalogRepository extends JpaRepository<ErrorCatalog, Long> {
    Stream<ErrorCatalog> findAllByService(String service);
}
