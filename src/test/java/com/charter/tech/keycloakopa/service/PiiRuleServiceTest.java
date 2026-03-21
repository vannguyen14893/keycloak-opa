package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.PiiRuleRequest;
import com.charter.tech.keycloakopa.dto.PiiRuleResponse;
import com.charter.tech.keycloakopa.entity.PiiRule;
import com.charter.tech.keycloakopa.mappper.PiiRuleMapper;
import com.charter.tech.keycloakopa.repository.PiiRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PiiRuleServiceTest {

    @Mock
    private PiiRuleRepository piiRuleRepository;

    @Mock
    private PiiRuleMapper piiRuleMapper;

    @InjectMocks
    private PiiRuleService piiRuleService;

    private PiiRule piiRule;
    private PiiRuleRequest piiRuleRequest;
    private PiiRuleResponse piiRuleResponse;

    @BeforeEach
    void setUp() {
        piiRule = new PiiRule(1L, "email", "***", true);
        piiRuleRequest = new PiiRuleRequest("email", "***", true);
        piiRuleResponse = new PiiRuleResponse(1L, "email", "***", true);
    }

    @Test
    void getAllPiiRule_shouldReturnMapOfActiveRules() {
        // Given
        Stream<PiiRule> activeRules = Stream.of(piiRule);
        when(piiRuleRepository.findAllByStatusIsTrue()).thenReturn(activeRules);

        // When
        Map<String, String> result = piiRuleService.getAllPiiRule();

        // Then
        assertEquals(1, result.size());
        assertEquals("***", result.get("email"));
        verify(piiRuleRepository).findAllByStatusIsTrue();
    }

    @Test
    void findAllPiiRule_shouldReturnListOfPiiRuleResponses() {
        // Given
        List<PiiRule> piiRules = List.of(piiRule);
        when(piiRuleRepository.findAll()).thenReturn(piiRules);
        when(piiRuleMapper.toResponse(piiRule)).thenReturn(piiRuleResponse);

        // When
        List<PiiRuleResponse> result = piiRuleService.findAllPiiRule();

        // Then
        assertEquals(1, result.size());
        assertEquals(piiRuleResponse, result.get(0));
        verify(piiRuleRepository).findAll();
        verify(piiRuleMapper).toResponse(piiRule);
    }

    @Test
    void findById_shouldReturnPiiRuleResponse_whenExists() {
        // Given
        when(piiRuleRepository.findById(1L)).thenReturn(Optional.of(piiRule));
        when(piiRuleMapper.toResponse(piiRule)).thenReturn(piiRuleResponse);

        // When
        PiiRuleResponse result = piiRuleService.findById(1L);

        // Then
        assertEquals(piiRuleResponse, result);
        verify(piiRuleRepository).findById(1L);
        verify(piiRuleMapper).toResponse(piiRule);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // Given
        when(piiRuleRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> piiRuleService.findById(1L));
        verify(piiRuleRepository).findById(1L);
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // Given
        when(piiRuleRepository.findById(1L)).thenReturn(Optional.of(piiRule));
        when(piiRuleRepository.save(any(PiiRule.class))).thenReturn(piiRule);

        // When
        Long result = piiRuleService.delete(1L);

        // Then
        assertEquals(1L, result);
        assertFalse(piiRule.getStatus());
        verify(piiRuleRepository).findById(1L);
        verify(piiRuleRepository).save(piiRule);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        // Given
        when(piiRuleRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> piiRuleService.delete(1L));
        verify(piiRuleRepository).findById(1L);
    }

    @Test
    void create_shouldReturnPiiRuleResponse_whenCreated() {
        // Given
        when(piiRuleMapper.toEntityCreate(piiRuleRequest)).thenReturn(piiRule);
        when(piiRuleRepository.save(piiRule)).thenReturn(piiRule);
        when(piiRuleMapper.toResponse(piiRule)).thenReturn(piiRuleResponse);

        // When
        PiiRuleResponse result = piiRuleService.create(piiRuleRequest);

        // Then
        assertEquals(piiRuleResponse, result);
        verify(piiRuleMapper).toEntityCreate(piiRuleRequest);
        verify(piiRuleRepository).save(piiRule);
        verify(piiRuleMapper).toResponse(piiRule);
    }

    @Test
    void update_shouldReturnPiiRuleResponse_whenUpdated() {
        // Given
        when(piiRuleRepository.findById(1L)).thenReturn(Optional.of(piiRule));
        when(piiRuleRepository.save(piiRule)).thenReturn(piiRule);
        when(piiRuleMapper.toResponse(piiRule)).thenReturn(piiRuleResponse);

        // When
        PiiRuleResponse result = piiRuleService.update(1L, piiRuleRequest);

        // Then
        assertEquals(piiRuleResponse, result);
        verify(piiRuleRepository).findById(1L);
        verify(piiRuleMapper).toEntityUpdate(piiRuleRequest, piiRule);
        verify(piiRuleRepository).save(piiRule);
        verify(piiRuleMapper).toResponse(piiRule);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Given
        when(piiRuleRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> piiRuleService.update(1L, piiRuleRequest));
        verify(piiRuleRepository).findById(1L);
    }

    @Test
    void convertToPiiRuleResponse_shouldReturnPiiRuleResponse() {
        // Given
        when(piiRuleMapper.toResponse(piiRule)).thenReturn(piiRuleResponse);

        // When
        PiiRuleResponse result = piiRuleService.convertToPiiRuleResponse(piiRule);

        // Then
        assertEquals(piiRuleResponse, result);
        verify(piiRuleMapper).toResponse(piiRule);
    }
}
