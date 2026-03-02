package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.client.OpaClient;
import com.charter.tech.keycloakopa.exception.CustomAccessDeniedException;
import com.charter.tech.keycloakopa.repository.PermissionScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
    private final PermissionScopeRepository repo;

    public void authorize(String resource, String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new CustomAccessDeniedException("Authentication not found");
        List<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(role -> role != null && role.startsWith("ROLE_")).toList();
        if (userRoles.isEmpty()) throw new CustomAccessDeniedException("Roles not found");
        Map<String, Object> request = Map.of(
                "input", Map.of(
                        "user", Map.of(
                                "role", userRoles.getFirst()
                        ),
                        "resource", resource,
                        "action", action
                )
        );
        boolean allowed = opaClient.allow(request);
        if (!allowed) {
            throw new CustomAccessDeniedException("OPA denied");
        }
    }
}

