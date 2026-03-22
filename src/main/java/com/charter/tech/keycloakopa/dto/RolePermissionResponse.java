package com.charter.tech.keycloakopa.dto;

import java.util.List;
import java.util.Map;

public record RolePermissionResponse(String roleName, Map<String, List<String>> permissions) {

}
