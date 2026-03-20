package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.SwaggerTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwaggerTranslationRepository extends JpaRepository<SwaggerTranslation, Long> {
    List<SwaggerTranslation> findByLocale(String locale);

}
