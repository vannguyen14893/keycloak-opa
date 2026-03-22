package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.client.OpaClient;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.RolePermissionResponse;
import com.charter.tech.keycloakopa.exception.CustomAccessDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private OpaClient opaClient;

    @Mock
    private RoleService roleService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthorizationService authorizationService;

    private List<GrantedAuthority> authorities;

    @BeforeEach
    void setUp() {
        authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Test
    void authorize_shouldThrowException_whenNoAuthentication() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);

            // When & Then
            CustomAccessDeniedException exception = assertThrows(CustomAccessDeniedException.class,
                    () -> authorizationService.authorize("products", "read"));
            assertEquals(ResponseCodeConstants.SEC_AUTH_REQUIRED, exception.getMessage());
        }
    }

    @Test
    void authorize_shouldThrowException_whenNoRoles() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            List<GrantedAuthority> noRoleAuthorities = List.of(new SimpleGrantedAuthority("USER"));
            when(authentication.getAuthorities()).thenReturn((Collection) noRoleAuthorities);

            // When & Then
            CustomAccessDeniedException exception = assertThrows(CustomAccessDeniedException.class,
                    () -> authorizationService.authorize("products", "read"));
            assertEquals(ResponseCodeConstants.SEC_ROLE_REQUIRED, exception.getMessage());
        }
    }

    @Test
    void authorize_shouldThrowException_whenPermissionDenied() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);
            List<RolePermissionResponse> rolePermissions = List.of(new RolePermissionResponse("ROLE_ADMIN", Map.of("products", List.of("read"))));
            when(roleService.findByCode("ROLE_ADMIN")).thenReturn(rolePermissions);
            when(opaClient.allow(any())).thenReturn(false);

            // When & Then
            CustomAccessDeniedException exception = assertThrows(CustomAccessDeniedException.class,
                    () -> authorizationService.authorize("products", "read"));
            assertEquals(ResponseCodeConstants.SEC_PERMISSION_DENIED, exception.getMessage());
        }
    }

    @Test
    void authorize_shouldNotThrowException_whenAllowed() {
        // Given
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);
            List<RolePermissionResponse> rolePermissions = List.of(new RolePermissionResponse("ROLE_ADMIN", Map.of("products", List.of("read"))));
            when(roleService.findByCode("ROLE_ADMIN")).thenReturn(rolePermissions);
            when(opaClient.allow(any())).thenReturn(true);

            // When & Then
            assertDoesNotThrow(() -> authorizationService.authorize("products", "read"));
        }
    }
}
