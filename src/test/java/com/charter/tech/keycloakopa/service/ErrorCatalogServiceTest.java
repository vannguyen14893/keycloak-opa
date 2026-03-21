package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.constans.Constants;
import com.charter.tech.keycloakopa.dto.ErrorCatalogRequest;
import com.charter.tech.keycloakopa.dto.ErrorCatalogResponse;
import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import com.charter.tech.keycloakopa.mappper.ErrorCatalogMapper;
import com.charter.tech.keycloakopa.repository.ErrorCatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErrorCatalogServiceTest {

    @Mock
    private ErrorCatalogRepository errorCatalogRepository;

    @Mock
    private ErrorCatalogMapper errorCatalogMapper;

    @InjectMocks
    private ErrorCatalogService errorCatalogService;

    private ErrorCatalog errorCatalog;
    private ErrorCatalogRequest errorCatalogRequest;
    private ErrorCatalogResponse errorCatalogResponse;

    @BeforeEach
    void setUp() {
        errorCatalog = new ErrorCatalog(1L, "ERR001", "VALIDATION", 400, "error.validation", "USER-SERVICE", "ERROR", false);
        errorCatalogRequest = new ErrorCatalogRequest("ERR001", "VALIDATION", 400, "error.validation", "USER-SERVICE", "ERROR", false);
        errorCatalogResponse = new ErrorCatalogResponse(1L, "ERR001", "VALIDATION", 400, "error.validation", "USER-SERVICE", "ERROR", false);
    }

    @Test
    void findAllByService_shouldReturnMapOfErrorCatalogs() {
        // Given
        Stream<ErrorCatalog> errorCatalogs = Stream.of(errorCatalog);
        when(errorCatalogRepository.findAllByService(Constants.USER_SERVICE)).thenReturn(errorCatalogs);

        // When
        Map<String, ErrorCatalog> result = errorCatalogService.findAllByService();

        // Then
        assertEquals(1, result.size());
        assertEquals(errorCatalog, result.get("ERR001"));
        verify(errorCatalogRepository).findAllByService(Constants.USER_SERVICE);
    }

    @Test
    void findAll_shouldReturnListOfErrorCatalogResponses() {
        // Given
        List<ErrorCatalog> errorCatalogs = List.of(errorCatalog);
        Page<ErrorCatalog> page = new PageImpl<>(errorCatalogs);
        when(errorCatalogRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(errorCatalogMapper.toResponse(errorCatalog)).thenReturn(errorCatalogResponse);

        // When
        List<ErrorCatalogResponse> result = errorCatalogService.findAll(0, 10);

        // Then
        assertEquals(1, result.size());
        assertEquals(errorCatalogResponse, result.get(0));
        verify(errorCatalogRepository).findAll(any(Pageable.class));
        verify(errorCatalogMapper).toResponse(errorCatalog);
    }

    @Test
    void findById_shouldReturnErrorCatalogResponse_whenExists() {
        // Given
        when(errorCatalogRepository.findById(1L)).thenReturn(Optional.of(errorCatalog));
        when(errorCatalogMapper.toResponse(errorCatalog)).thenReturn(errorCatalogResponse);

        // When
        ErrorCatalogResponse result = errorCatalogService.findById(1L);

        // Then
        assertEquals(errorCatalogResponse, result);
        verify(errorCatalogRepository).findById(1L);
        verify(errorCatalogMapper).toResponse(errorCatalog);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // Given
        when(errorCatalogRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> errorCatalogService.findById(1L));
        verify(errorCatalogRepository).findById(1L);
    }

    @Test
    void create_shouldReturnErrorCatalogResponse_whenCreated() {
        // Given
        when(errorCatalogMapper.toEntityCreate(errorCatalogRequest)).thenReturn(errorCatalog);
        when(errorCatalogRepository.save(errorCatalog)).thenReturn(errorCatalog);
        when(errorCatalogMapper.toResponse(errorCatalog)).thenReturn(errorCatalogResponse);

        // When
        ErrorCatalogResponse result = errorCatalogService.create(errorCatalogRequest);

        // Then
        assertEquals(errorCatalogResponse, result);
        verify(errorCatalogMapper).toEntityCreate(errorCatalogRequest);
        verify(errorCatalogRepository).save(errorCatalog);
        verify(errorCatalogMapper).toResponse(errorCatalog);
    }

    @Test
    void update_shouldReturnErrorCatalogResponse_whenUpdated() {
        // Given
        when(errorCatalogRepository.findById(1L)).thenReturn(Optional.of(errorCatalog));
        when(errorCatalogRepository.save(errorCatalog)).thenReturn(errorCatalog);
        when(errorCatalogMapper.toResponse(errorCatalog)).thenReturn(errorCatalogResponse);

        // When
        ErrorCatalogResponse result = errorCatalogService.update(1L, errorCatalogRequest);

        // Then
        assertEquals(errorCatalogResponse, result);
        verify(errorCatalogRepository).findById(1L);
        verify(errorCatalogMapper).toEntityUpdate(errorCatalogRequest, errorCatalog);
        verify(errorCatalogRepository).save(errorCatalog);
        verify(errorCatalogMapper).toResponse(errorCatalog);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Given
        when(errorCatalogRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> errorCatalogService.update(1L, errorCatalogRequest));
        verify(errorCatalogRepository).findById(1L);
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // When
        Long result = errorCatalogService.delete(1L);

        // Then
        assertEquals(1L, result);
        verify(errorCatalogRepository).deleteById(1L);
    }

    @Test
    void convertToErrorCatalogResponse_shouldReturnErrorCatalogResponse() {
        // Given
        when(errorCatalogMapper.toResponse(errorCatalog)).thenReturn(errorCatalogResponse);

        // When
        ErrorCatalogResponse result = errorCatalogService.convertToErrorCatalogResponse(errorCatalog);

        // Then
        assertEquals(errorCatalogResponse, result);
        verify(errorCatalogMapper).toResponse(errorCatalog);
    }
}
