package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.SwaggerTranslationRequest;
import com.charter.tech.keycloakopa.dto.SwaggerTranslationResponse;
import com.charter.tech.keycloakopa.entity.SwaggerTranslation;
import com.charter.tech.keycloakopa.mappper.SwaggerTranslationMapper;
import com.charter.tech.keycloakopa.repository.SwaggerTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwaggerTranslationServiceTest {

    @Mock
    private SwaggerTranslationRepository swaggerTranslationRepository;

    @Mock
    private SwaggerTranslationMapper swaggerTranslationMapper;

    @InjectMocks
    private SwaggerTranslationService swaggerTranslationService;

    private SwaggerTranslation swaggerTranslation;
    private SwaggerTranslationRequest swaggerTranslationRequest;
    private SwaggerTranslationResponse swaggerTranslationResponse;

    @BeforeEach
    void setUp() {
        swaggerTranslation = new SwaggerTranslation(1L, "user.create", "en", "Create User", "Creates a new user");
        swaggerTranslationRequest = new SwaggerTranslationRequest("user.create", "en", "Create User", "Creates a new user");
        swaggerTranslationResponse = new SwaggerTranslationResponse(1L, "user.create", "en", "Create User", "Creates a new user");
    }

    @Test
    void findAll_shouldReturnListOfSwaggerTranslationResponses() {
        // Given
        List<SwaggerTranslation> swaggerTranslationList = List.of(swaggerTranslation);
        when(swaggerTranslationRepository.findAll()).thenReturn(swaggerTranslationList);
        when(swaggerTranslationMapper.toResponse(swaggerTranslation)).thenReturn(swaggerTranslationResponse);

        // When
        List<SwaggerTranslationResponse> result = swaggerTranslationService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(swaggerTranslationResponse, result.get(0));
        verify(swaggerTranslationRepository).findAll();
        verify(swaggerTranslationMapper).toResponse(swaggerTranslation);
    }

    @Test
    void findById_shouldReturnSwaggerTranslationResponse_whenExists() {
        // Given
        when(swaggerTranslationRepository.findById(1L)).thenReturn(Optional.of(swaggerTranslation));
        when(swaggerTranslationMapper.toResponse(swaggerTranslation)).thenReturn(swaggerTranslationResponse);

        // When
        SwaggerTranslationResponse result = swaggerTranslationService.findById(1L);

        // Then
        assertEquals(swaggerTranslationResponse, result);
        verify(swaggerTranslationRepository).findById(1L);
        verify(swaggerTranslationMapper).toResponse(swaggerTranslation);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // Given
        when(swaggerTranslationRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> swaggerTranslationService.findById(1L));
        verify(swaggerTranslationRepository).findById(1L);
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // Given
        // No need to mock save since it's hard delete

        // When
        Long result = swaggerTranslationService.delete(1L);

        // Then
        assertEquals(1L, result);
        verify(swaggerTranslationRepository).deleteById(1L);
    }

    @Test
    void create_shouldReturnSwaggerTranslationResponse_whenCreated() {
        // Given
        when(swaggerTranslationMapper.toEntityCreate(swaggerTranslationRequest)).thenReturn(swaggerTranslation);
        when(swaggerTranslationRepository.save(swaggerTranslation)).thenReturn(swaggerTranslation);
        when(swaggerTranslationMapper.toResponse(swaggerTranslation)).thenReturn(swaggerTranslationResponse);

        // When
        SwaggerTranslationResponse result = swaggerTranslationService.create(swaggerTranslationRequest);

        // Then
        assertEquals(swaggerTranslationResponse, result);
        verify(swaggerTranslationMapper).toEntityCreate(swaggerTranslationRequest);
        verify(swaggerTranslationRepository).save(swaggerTranslation);
        verify(swaggerTranslationMapper).toResponse(swaggerTranslation);
    }

    @Test
    void update_shouldReturnSwaggerTranslationResponse_whenUpdated() {
        // Given
        when(swaggerTranslationRepository.findById(1L)).thenReturn(Optional.of(swaggerTranslation));
        when(swaggerTranslationRepository.save(swaggerTranslation)).thenReturn(swaggerTranslation);
        when(swaggerTranslationMapper.toResponse(swaggerTranslation)).thenReturn(swaggerTranslationResponse);

        // When
        SwaggerTranslationResponse result = swaggerTranslationService.update(1L, swaggerTranslationRequest);

        // Then
        assertEquals(swaggerTranslationResponse, result);
        verify(swaggerTranslationRepository).findById(1L);
        verify(swaggerTranslationMapper).toEntityUpdate(swaggerTranslationRequest, swaggerTranslation);
        verify(swaggerTranslationRepository).save(swaggerTranslation);
        verify(swaggerTranslationMapper).toResponse(swaggerTranslation);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Given
        when(swaggerTranslationRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> swaggerTranslationService.update(1L, swaggerTranslationRequest));
        verify(swaggerTranslationRepository).findById(1L);
    }

    @Test
    void convertToSwaggerTranslationResponse_shouldReturnSwaggerTranslationResponse() {
        // Given
        when(swaggerTranslationMapper.toResponse(swaggerTranslation)).thenReturn(swaggerTranslationResponse);

        // When
        SwaggerTranslationResponse result = swaggerTranslationService.convertToSwaggerTranslationResponse(swaggerTranslation);

        // Then
        assertEquals(swaggerTranslationResponse, result);
        verify(swaggerTranslationMapper).toResponse(swaggerTranslation);
    }
}
