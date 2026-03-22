package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.client.OpaClient;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.RolePermissionResponse;
import com.charter.tech.keycloakopa.exception.CustomAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final OpaClient opaClient;

    private final RoleService roleService;

    public void authorize(String resource, String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new CustomAccessDeniedException(ResponseCodeConstants.SEC_AUTH_REQUIRED);
        List<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(role -> role != null && role.startsWith("ROLE_")).toList();
        if (userRoles.isEmpty()) throw new CustomAccessDeniedException(ResponseCodeConstants.SEC_ROLE_REQUIRED);
        List<RolePermissionResponse> rolePermissionResponses = roleService.findByCode(userRoles.getFirst());
        Map<String, Object> request = Map.of(
                "input", Map.of(
                        "user", Map.of(
                                "role", userRoles.getFirst()
                        ),
                        "resource", resource,
                        "action", action,
                        "role_permissions",rolePermissionResponses

                )
        );
        boolean allowed = opaClient.allow(request);
        if (!allowed) {
            throw new CustomAccessDeniedException(ResponseCodeConstants.SEC_PERMISSION_DENIED);
        }
    }
}

