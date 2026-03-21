package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.LanguagesRequest;
import com.charter.tech.keycloakopa.dto.LanguagesResponse;
import com.charter.tech.keycloakopa.entity.Languages;
import com.charter.tech.keycloakopa.mappper.LanguagesMapper;
import com.charter.tech.keycloakopa.repository.LanguagesRepository;
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
class LanguagesServiceTest {

    @Mock
    private LanguagesRepository languagesRepository;

    @Mock
    private LanguagesMapper languagesMapper;

    @InjectMocks
    private LanguagesService languagesService;

    private Languages languages;
    private LanguagesRequest languagesRequest;
    private LanguagesResponse languagesResponse;

    @BeforeEach
    void setUp() {
        languages = new Languages(1L, "EN", "English", true);
        languagesRequest = new LanguagesRequest("EN", "English", true);
        languagesResponse = new LanguagesResponse(1L, "EN", "English", true);
    }

    @Test
    void findAll_shouldReturnListOfLanguagesResponses() {
        // Given
        List<Languages> languagesList = List.of(languages);
        when(languagesRepository.findAll()).thenReturn(languagesList);
        when(languagesMapper.toResponse(languages)).thenReturn(languagesResponse);

        // When
        List<LanguagesResponse> result = languagesService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(languagesResponse, result.get(0));
        verify(languagesRepository).findAll();
        verify(languagesMapper).toResponse(languages);
    }

    @Test
    void findById_shouldReturnLanguagesResponse_whenExists() {
        // Given
        when(languagesRepository.findById(1L)).thenReturn(Optional.of(languages));
        when(languagesMapper.toResponse(languages)).thenReturn(languagesResponse);

        // When
        LanguagesResponse result = languagesService.findById(1L);

        // Then
        assertEquals(languagesResponse, result);
        verify(languagesRepository).findById(1L);
        verify(languagesMapper).toResponse(languages);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // Given
        when(languagesRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> languagesService.findById(1L));
        verify(languagesRepository).findById(1L);
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // Given
        when(languagesRepository.findById(1L)).thenReturn(Optional.of(languages));
        when(languagesRepository.save(any(Languages.class))).thenReturn(languages);

        // When
        Long result = languagesService.delete(1L);

        // Then
        assertEquals(1L, result);
        assertFalse(languages.getStatus());
        verify(languagesRepository).findById(1L);
        verify(languagesRepository).save(languages);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        // Given
        when(languagesRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> languagesService.delete(1L));
        verify(languagesRepository).findById(1L);
    }

    @Test
    void create_shouldReturnLanguagesResponse_whenCreated() {
        // Given
        when(languagesMapper.toEntityCreate(languagesRequest)).thenReturn(languages);
        when(languagesRepository.save(languages)).thenReturn(languages);
        when(languagesMapper.toResponse(languages)).thenReturn(languagesResponse);

        // When
        LanguagesResponse result = languagesService.create(languagesRequest);

        // Then
        assertEquals(languagesResponse, result);
        verify(languagesMapper).toEntityCreate(languagesRequest);
        verify(languagesRepository).save(languages);
        verify(languagesMapper).toResponse(languages);
    }

    @Test
    void update_shouldReturnLanguagesResponse_whenUpdated() {
        // Given
        when(languagesRepository.findById(1L)).thenReturn(Optional.of(languages));
        when(languagesRepository.save(languages)).thenReturn(languages);
        when(languagesMapper.toResponse(languages)).thenReturn(languagesResponse);

        // When
        LanguagesResponse result = languagesService.update(1L, languagesRequest);

        // Then
        assertEquals(languagesResponse, result);
        verify(languagesRepository).findById(1L);
        verify(languagesMapper).toEntityUpdate(languagesRequest, languages);
        verify(languagesRepository).save(languages);
        verify(languagesMapper).toResponse(languages);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Given
        when(languagesRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> languagesService.update(1L, languagesRequest));
        verify(languagesRepository).findById(1L);
    }

    @Test
    void convertToLanguagesResponse_shouldReturnLanguagesResponse() {
        // Given
        when(languagesMapper.toResponse(languages)).thenReturn(languagesResponse);

        // When
        LanguagesResponse result = languagesService.convertToLanguagesResponse(languages);

        // Then
        assertEquals(languagesResponse, result);
        verify(languagesMapper).toResponse(languages);
    }
}
