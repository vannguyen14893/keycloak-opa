package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.PiiRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface PiiRuleRepository extends JpaRepository<PiiRule, Long> {
    Stream<PiiRule> findAllByStatusIsTrue();
}
