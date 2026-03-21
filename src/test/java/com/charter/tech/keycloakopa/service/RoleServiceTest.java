package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.constans.MessageConstants;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.entity.Role;
import com.charter.tech.keycloakopa.exception.BusinessExceptionHandler;
import com.charter.tech.keycloakopa.mappper.RoleMapper;
import com.charter.tech.keycloakopa.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private DBMessageSourceConfig dbMessageSourceConfig;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    private Role role;
    private RoleRequest roleRequest;
    private RoleResponse roleResponse;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setCode("ADMIN");
        role.setName("Administrator");

        roleRequest = new RoleRequest("ADMIN", "Administrator");

        roleResponse = new RoleResponse(1L, "ADMIN", "Administrator");
    }

    @Test
    void findAll_shouldReturnListOfRoleResponses() {
        // Given
        List<Role> roles = List.of(role);
        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.toResponse(role)).thenReturn(roleResponse);

        // When
        List<RoleResponse> result = roleService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(roleResponse, result.get(0));
        verify(roleRepository).findAll();
        verify(roleMapper).toResponse(role);
    }

    @Test
    void findById_shouldReturnRoleResponse_whenRoleExists() {
        // Given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toResponse(role)).thenReturn(roleResponse);

        // When
        RoleResponse result = roleService.findById(1L);

        // Then
        assertEquals(roleResponse, result);
        verify(roleRepository).findById(1L);
        verify(roleMapper).toResponse(role);
    }

    @Test
    void findById_shouldThrowException_whenRoleNotFound() {
        // Given
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        when(httpServletRequest.getLocale()).thenReturn(Locale.ENGLISH);
        when(dbMessageSourceConfig.getMessages(eq(MessageConstants.VAL_ROLE_NOT_FOUND_ID), any(), eq(Locale.ENGLISH)))
                .thenReturn("Role not found with id: 1");

        // When & Then
        BusinessExceptionHandler exception = assertThrows(BusinessExceptionHandler.class, () -> roleService.findById(1L));
        assertEquals(ResponseCodeConstants.VAL_ROLE_NOT_FOUND_ID, exception.getCode());
        assertEquals("Role not found with id: 1", exception.getMessage());
        verify(roleRepository).findById(1L);
        verify(dbMessageSourceConfig).getMessages(eq(MessageConstants.VAL_ROLE_NOT_FOUND_ID), any(), eq(Locale.ENGLISH));
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // When
        Long result = roleService.delete(1L);

        // Then
        assertEquals(1L, result);
        verify(roleRepository).deleteById(1L);
    }

    @Test
    void create_shouldReturnRoleResponse_whenCreated() {
        // Given
        when(roleMapper.toEntityCreate(roleRequest)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toResponse(role)).thenReturn(roleResponse);

        // When
        RoleResponse result = roleService.create(roleRequest);

        // Then
        assertEquals(roleResponse, result);
        verify(roleMapper).toEntityCreate(roleRequest);
        verify(roleRepository).save(role);
        verify(roleMapper).toResponse(role);
    }

    @Test
    void update_shouldReturnRoleResponse_whenUpdated() {
        // Given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toResponse(role)).thenReturn(roleResponse);

        // When
        RoleResponse result = roleService.update(1L, roleRequest);

        // Then
        assertEquals(roleResponse, result);
        verify(roleRepository).findById(1L);
        verify(roleMapper).toEntityUpdate(roleRequest, role);
        verify(roleRepository).save(role);
        verify(roleMapper).toResponse(role);
    }

    @Test
    void update_shouldThrowException_whenRoleNotFound() {
        // Given
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        when(httpServletRequest.getLocale()).thenReturn(Locale.ENGLISH);
        when(dbMessageSourceConfig.getMessages(eq(MessageConstants.VAL_ROLE_NOT_FOUND_ID), any(), eq(Locale.ENGLISH)))
                .thenReturn("Role not found with id: 1");

        // When & Then
        BusinessExceptionHandler exception = assertThrows(BusinessExceptionHandler.class, () -> roleService.update(1L, roleRequest));
        assertEquals(ResponseCodeConstants.VAL_ROLE_NOT_FOUND_ID, exception.getCode());
        assertEquals("Role not found with id: 1", exception.getMessage());
        verify(roleRepository).findById(1L);
        verify(dbMessageSourceConfig).getMessages(eq(MessageConstants.VAL_ROLE_NOT_FOUND_ID), any(), eq(Locale.ENGLISH));
    }

    @Test
    void convertToRoleResponse_shouldReturnRoleResponse() {
        // Given
        when(roleMapper.toResponse(role)).thenReturn(roleResponse);

        // When
        RoleResponse result = roleService.convertToRoleResponse(role);

        // Then
        assertEquals(roleResponse, result);
        verify(roleMapper).toResponse(role);
    }
}
