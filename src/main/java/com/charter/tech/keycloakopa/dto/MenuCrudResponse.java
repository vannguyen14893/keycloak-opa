package com.charter.tech.keycloakopa.dto;

public record MenuCrudResponse(Long id, Long parentId, String path, String icon, Integer orderNo) {
}
