package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.PermissionRequest;
import com.charter.tech.keycloakopa.dto.PermissionResponse;
import com.charter.tech.keycloakopa.entity.Permission;
import com.charter.tech.keycloakopa.mappper.PermissionMapper;
import com.charter.tech.keycloakopa.repository.PermissionRepository;
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
class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private PermissionService permissionService;

    private Permission permission;
    private PermissionRequest permissionRequest;
    private PermissionResponse permissionResponse;

    @BeforeEach
    void setUp() {
        permission = new Permission(1L, "READ_USER", "Read user permission", null, null);
        permissionRequest = new PermissionRequest("READ_USER", "Read user permission");
        permissionResponse = new PermissionResponse(1L, "READ_USER", "Read user permission");
    }

    @Test
    void findAll_shouldReturnListOfPermissionResponses() {
        // Given
        List<Permission> permissionList = List.of(permission);
        when(permissionRepository.findAll()).thenReturn(permissionList);
        when(permissionMapper.toResponse(permission)).thenReturn(permissionResponse);

        // When
        List<PermissionResponse> result = permissionService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(permissionResponse, result.get(0));
        verify(permissionRepository).findAll();
        verify(permissionMapper).toResponse(permission);
    }

    @Test
    void findById_shouldReturnPermissionResponse_whenExists() {
        // Given
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));
        when(permissionMapper.toResponse(permission)).thenReturn(permissionResponse);

        // When
        PermissionResponse result = permissionService.findById(1L);

        // Then
        assertEquals(permissionResponse, result);
        verify(permissionRepository).findById(1L);
        verify(permissionMapper).toResponse(permission);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // Given
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> permissionService.findById(1L));
        verify(permissionRepository).findById(1L);
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // Given
        // No need to mock save since it's hard delete

        // When
        Long result = permissionService.delete(1L);

        // Then
        assertEquals(1L, result);
        verify(permissionRepository).deleteById(1L);
    }

    @Test
    void create_shouldReturnPermissionResponse_whenCreated() {
        // Given
        when(permissionMapper.toEntityCreate(permissionRequest)).thenReturn(permission);
        when(permissionRepository.save(permission)).thenReturn(permission);
        when(permissionMapper.toResponse(permission)).thenReturn(permissionResponse);

        // When
        PermissionResponse result = permissionService.create(permissionRequest);

        // Then
        assertEquals(permissionResponse, result);
        verify(permissionMapper).toEntityCreate(permissionRequest);
        verify(permissionRepository).save(permission);
        verify(permissionMapper).toResponse(permission);
    }

    @Test
    void update_shouldReturnPermissionResponse_whenUpdated() {
        // Given
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));
        when(permissionRepository.save(permission)).thenReturn(permission);
        when(permissionMapper.toResponse(permission)).thenReturn(permissionResponse);

        // When
        PermissionResponse result = permissionService.update(1L, permissionRequest);

        // Then
        assertEquals(permissionResponse, result);
        verify(permissionRepository).findById(1L);
        verify(permissionMapper).toEntityUpdate(permissionRequest, permission);
        verify(permissionRepository).save(permission);
        verify(permissionMapper).toResponse(permission);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Given
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> permissionService.update(1L, permissionRequest));
        verify(permissionRepository).findById(1L);
    }

    @Test
    void convertToPermissionResponse_shouldReturnPermissionResponse() {
        // Given
        when(permissionMapper.toResponse(permission)).thenReturn(permissionResponse);

        // When
        PermissionResponse result = permissionService.convertToPermissionResponse(permission);

        // Then
        assertEquals(permissionResponse, result);
        verify(permissionMapper).toResponse(permission);
    }
}
