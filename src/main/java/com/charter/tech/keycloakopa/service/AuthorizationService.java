package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.client.OpaClient;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.ErrorCatalogResponse;
import com.charter.tech.keycloakopa.dto.RolePermissionResponse;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import com.charter.tech.keycloakopa.exception.CustomAccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {

    private final OpaClient opaClient;

    private final RoleService roleService;
    //@Retryable(maxRetries = 2, delay = 5000, multiplier = 2, maxDelay = 7000,excludes = CustomAccessDeniedException.class)
   // @ConcurrencyLimit(3)
    public void authorize(String resource, String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) throw new CustomAccessDeniedException(ResponseCodeConstants.SEC_AUTH_REQUIRED);
        List<String> userRoles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(role -> role != null && role.startsWith("ROLE_"))
                .collect(Collectors.toList());
        if (userRoles.isEmpty()) throw new CustomAccessDeniedException(ResponseCodeConstants.SEC_ROLE_REQUIRED);

        String userRole = userRoles.get(0);

        List<RolePermissionResponse> rolePermissionResponses = roleService.findByCode(userRole);
        Map<String, Object> request = Map.of(
                "input", Map.of(
                        "user", Map.of(
                                "role", userRole
                        ),
                        "resource", resource,
                        "action", action,
                        "role_permissions", rolePermissionResponses

                )
        );
        SuccessResultResponse<ErrorCatalogResponse> errorCatalogById = opaClient.findErrorCatalogById(1L);
        //log.info("OPA Response: {}", errorCatalogById);
//        boolean allowed = opaClient.allow(request).isResult();
//        if (!allowed) {
//            throw new CustomAccessDeniedException(ResponseCodeConstants.SEC_PERMISSION_DENIED);
//        }
    }
}
