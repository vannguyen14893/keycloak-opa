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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public RoleResponse convertToRoleResponse(Role role) {
        return roleMapper.toResponse(role);
    }
}
