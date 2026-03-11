package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.LanguagesDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface LanguagesDetailRepository extends JpaRepository<LanguagesDetail, Long> {
    Stream<LanguagesDetail> findAllByLanguages_CodeAndLanguages_StatusIsTrue(String languagesCode);
}
