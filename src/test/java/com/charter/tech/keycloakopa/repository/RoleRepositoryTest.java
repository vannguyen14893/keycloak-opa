package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class RoleRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.liquibase.enabled", () -> "false");
    }

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByCode_shouldReturnRole_whenExists() {
        // Given
        Role role = new Role();
        role.setCode("TEST_ROLE");
        role.setName("Test Role");
        roleRepository.save(role);

        // When
        var result = roleRepository.findByCode("TEST_ROLE");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("TEST_ROLE");
    }

    @Test
    void findByCode_shouldReturnEmpty_whenNotExists() {
        // When
        var result = roleRepository.findByCode("NON_EXISTENT");

        // Then
        assertThat(result).isEmpty();
    }
}
