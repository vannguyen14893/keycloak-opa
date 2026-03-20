package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.entity.SwaggerTranslation;
import com.charter.tech.keycloakopa.repository.SwaggerTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SwaggerTranslationService {
    private final SwaggerTranslationRepository swaggerTranslationRepository;

    public Map<String, String> getByLocale(String locale) {
        return swaggerTranslationRepository.findByLocale(locale)
                .stream()
                .collect(Collectors.toMap(
                        SwaggerTranslation::getKey,
                        SwaggerTranslation::getName
                ));
    }
}
