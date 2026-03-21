package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.PermissionRequest;
import com.charter.tech.keycloakopa.dto.PermissionResponse;
import com.charter.tech.keycloakopa.entity.Permission;
import com.charter.tech.keycloakopa.mappper.PermissionMapper;
import com.charter.tech.keycloakopa.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll().stream().map(this::convertToPermissionResponse).toList();
    }

    public PermissionResponse findById(Long id) {
        return convertToPermissionResponse(permissionRepository.findById(id).orElseThrow());
    }

    public Long delete(Long id) {
        permissionRepository.deleteById(id);
        return id;
    }

    public PermissionResponse create(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toEntityCreate(permissionRequest);
        return convertToPermissionResponse(permissionRepository.save(permission));
    }

    public PermissionResponse update(Long id, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(id).orElseThrow();
        permissionMapper.toEntityUpdate(permissionRequest, permission);
        return convertToPermissionResponse(permissionRepository.save(permission));
    }

    public PermissionResponse convertToPermissionResponse(Permission permission) {
        return permissionMapper.toResponse(permission);
    }
}
