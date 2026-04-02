package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.constans.MessageConstants;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.RolePermissionResponse;
import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.entity.Permission;
import com.charter.tech.keycloakopa.entity.Role;
import com.charter.tech.keycloakopa.entity.Scopes;
import com.charter.tech.keycloakopa.exception.BusinessExceptionHandler;
import com.charter.tech.keycloakopa.mappper.RoleMapper;
import com.charter.tech.keycloakopa.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final DBMessageSourceConfig dbMessageSourceConfig;
    private final HttpServletRequest httpServletRequest;
    private final RoleMapper roleMapper;
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream().map(this::convertToRoleResponse).toList();
    }

    public RoleResponse findById(Long id) {
        String messages = dbMessageSourceConfig.getMessages(MessageConstants.VAL_ROLE_NOT_FOUND_ID, new Object[]{id}, httpServletRequest.getLocale());
        return convertToRoleResponse(roleRepository.findById(id).orElseThrow(() -> new BusinessExceptionHandler(ResponseCodeConstants.VAL_ROLE_NOT_FOUND_ID, messages)));
    }

    @Cacheable(value = "rolePermissions", cacheManager = "cacheManager")
    @Transactional(readOnly = true)
    public List<RolePermissionResponse> findByCode(String code) {
        List<Role> roles = roleRepository.findByCode(code);
        List<RolePermissionResponse> rolePermissionResponses =new ArrayList<>();
        Map<String,List<String>> mapPermissions = new HashMap<>();
        for (Role  role: roles){
            Set<Permission> rolePermissions = role.getPermissions();
            for (Permission permission: rolePermissions){
                Set<Scopes> scopes = permission.getScopes();
                List<String> scopeNames = scopes.stream().map(Scopes::getName).toList();
                mapPermissions.put(permission.getName(),scopeNames);
            }
            RolePermissionResponse rolePermissionResponse = new RolePermissionResponse(role.getCode(),mapPermissions);
            rolePermissionResponses.add(rolePermissionResponse);
        }
        return rolePermissionResponses;
    }

    public Long delete(Long id) {
        roleRepository.deleteById(id);
        return id;
    }

    public RoleResponse create(RoleRequest roleRequest) {
        Role role = roleMapper.toEntityCreate(roleRequest);
        return convertToRoleResponse(roleRepository.save(role));
    }

    public RoleResponse update(Long id, RoleRequest roleRequest) {
        String messages = dbMessageSourceConfig.getMessages(MessageConstants.VAL_ROLE_NOT_FOUND_ID, new Object[]{id}, httpServletRequest.getLocale());
        Role role = roleRepository.findById(id).orElseThrow(() -> new BusinessExceptionHandler(ResponseCodeConstants.VAL_ROLE_NOT_FOUND_ID, messages));
        roleMapper.toEntityUpdate(roleRequest, role);
        return convertToRoleResponse(roleRepository.save(role));
    }
    public static String crc16(String data) {
        int crc = 0xFFFF;
        for (char c : data.toCharArray()) {
            crc ^= (c << 8);
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ 0x1021;
                } else {
                    crc <<= 1;
                }
            }
        }
        crc &= 0xFFFF;
        return String.format("%04X", crc);
    }
    public RoleResponse convertToRoleResponse(Role role) {
        return roleMapper.toResponse(role);
    }
}
