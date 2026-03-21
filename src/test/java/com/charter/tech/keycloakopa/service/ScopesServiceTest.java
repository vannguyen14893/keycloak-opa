package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.ScopesRequest;
import com.charter.tech.keycloakopa.dto.ScopesResponse;
import com.charter.tech.keycloakopa.entity.Scopes;
import com.charter.tech.keycloakopa.mappper.ScopesMapper;
import com.charter.tech.keycloakopa.repository.ScopesRepository;
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
class ScopesServiceTest {

    @Mock
    private ScopesRepository scopesRepository;

    @Mock
    private ScopesMapper scopesMapper;

    @InjectMocks
    private ScopesService scopesService;

    private Scopes scopes;
    private ScopesRequest scopesRequest;
    private ScopesResponse scopesResponse;

    @BeforeEach
    void setUp() {
        scopes = new Scopes(1L, "READ", "Read scope", null);
        scopesRequest = new ScopesRequest("READ", "Read scope");
        scopesResponse = new ScopesResponse(1L, "READ", "Read scope");
    }

    @Test
    void findAll_shouldReturnListOfScopesResponses() {
        // Given
        List<Scopes> scopesList = List.of(scopes);
        when(scopesRepository.findAll()).thenReturn(scopesList);
        when(scopesMapper.toResponse(scopes)).thenReturn(scopesResponse);

        // When
        List<ScopesResponse> result = scopesService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(scopesResponse, result.get(0));
        verify(scopesRepository).findAll();
        verify(scopesMapper).toResponse(scopes);
    }

    @Test
    void findById_shouldReturnScopesResponse_whenExists() {
        // Given
        when(scopesRepository.findById(1L)).thenReturn(Optional.of(scopes));
        when(scopesMapper.toResponse(scopes)).thenReturn(scopesResponse);

        // When
        ScopesResponse result = scopesService.findById(1L);

        // Then
        assertEquals(scopesResponse, result);
        verify(scopesRepository).findById(1L);
        verify(scopesMapper).toResponse(scopes);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // Given
        when(scopesRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> scopesService.findById(1L));
        verify(scopesRepository).findById(1L);
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // Given
        // No need to mock save since it's hard delete

        // When
        Long result = scopesService.delete(1L);

        // Then
        assertEquals(1L, result);
        verify(scopesRepository).deleteById(1L);
    }

    @Test
    void create_shouldReturnScopesResponse_whenCreated() {
        // Given
        when(scopesMapper.toEntityCreate(scopesRequest)).thenReturn(scopes);
        when(scopesRepository.save(scopes)).thenReturn(scopes);
        when(scopesMapper.toResponse(scopes)).thenReturn(scopesResponse);

        // When
        ScopesResponse result = scopesService.create(scopesRequest);

        // Then
        assertEquals(scopesResponse, result);
        verify(scopesMapper).toEntityCreate(scopesRequest);
        verify(scopesRepository).save(scopes);
        verify(scopesMapper).toResponse(scopes);
    }

    @Test
    void update_shouldReturnScopesResponse_whenUpdated() {
        // Given
        when(scopesRepository.findById(1L)).thenReturn(Optional.of(scopes));
        when(scopesRepository.save(scopes)).thenReturn(scopes);
        when(scopesMapper.toResponse(scopes)).thenReturn(scopesResponse);

        // When
        ScopesResponse result = scopesService.update(1L, scopesRequest);

        // Then
        assertEquals(scopesResponse, result);
        verify(scopesRepository).findById(1L);
        verify(scopesMapper).toEntityUpdate(scopesRequest, scopes);
        verify(scopesRepository).save(scopes);
        verify(scopesMapper).toResponse(scopes);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Given
        when(scopesRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> scopesService.update(1L, scopesRequest));
        verify(scopesRepository).findById(1L);
    }

    @Test
    void convertToScopesResponse_shouldReturnScopesResponse() {
        // Given
        when(scopesMapper.toResponse(scopes)).thenReturn(scopesResponse);

        // When
        ScopesResponse result = scopesService.convertToScopesResponse(scopes);

        // Then
        assertEquals(scopesResponse, result);
        verify(scopesMapper).toResponse(scopes);
    }
}
