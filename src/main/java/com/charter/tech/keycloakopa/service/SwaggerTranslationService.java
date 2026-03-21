package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.SwaggerTranslationRequest;
import com.charter.tech.keycloakopa.dto.SwaggerTranslationResponse;
import com.charter.tech.keycloakopa.entity.SwaggerTranslation;
import com.charter.tech.keycloakopa.mappper.SwaggerTranslationMapper;
import com.charter.tech.keycloakopa.repository.SwaggerTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SwaggerTranslationService {
    private final SwaggerTranslationRepository swaggerTranslationRepository;
    private final SwaggerTranslationMapper swaggerTranslationMapper;

    public Map<String, String> getByLocale(String locale) {
        return swaggerTranslationRepository.findByLocale(locale)
                .stream()
                .collect(Collectors.toMap(
                        SwaggerTranslation::getKey,
                        SwaggerTranslation::getName
                ));
    }

    public List<SwaggerTranslationResponse> findAll() {
        return swaggerTranslationRepository.findAll().stream().map(this::convertToSwaggerTranslationResponse).toList();
    }

    public SwaggerTranslationResponse findById(Long id) {
        return convertToSwaggerTranslationResponse(swaggerTranslationRepository.findById(id).orElseThrow());
    }

    public Long delete(Long id) {
        swaggerTranslationRepository.deleteById(id);
        return id;
    }

    public SwaggerTranslationResponse create(SwaggerTranslationRequest swaggerTranslationRequest) {
        SwaggerTranslation swaggerTranslation = swaggerTranslationMapper.toEntityCreate(swaggerTranslationRequest);
        return convertToSwaggerTranslationResponse(swaggerTranslationRepository.save(swaggerTranslation));
    }

    public SwaggerTranslationResponse update(Long id, SwaggerTranslationRequest swaggerTranslationRequest) {
        SwaggerTranslation swaggerTranslation = swaggerTranslationRepository.findById(id).orElseThrow();
        swaggerTranslationMapper.toEntityUpdate(swaggerTranslationRequest, swaggerTranslation);
        return convertToSwaggerTranslationResponse(swaggerTranslationRepository.save(swaggerTranslation));
    }

    public SwaggerTranslationResponse convertToSwaggerTranslationResponse(SwaggerTranslation swaggerTranslation) {
        return swaggerTranslationMapper.toResponse(swaggerTranslation);
    }
}
