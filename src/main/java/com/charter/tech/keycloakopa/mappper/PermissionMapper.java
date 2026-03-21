package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.PermissionRequest;
import com.charter.tech.keycloakopa.dto.PermissionResponse;
import com.charter.tech.keycloakopa.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PermissionMapper extends BaseMapper<Permission, PermissionRequest, PermissionResponse> {

    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

}
