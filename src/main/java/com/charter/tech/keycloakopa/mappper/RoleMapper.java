package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponse toResponse(Role role);

    Role toEntityCreate(RoleRequest roleRequest);

    void toEntityUpdate(RoleRequest roleRequest, @MappingTarget Role role);
}