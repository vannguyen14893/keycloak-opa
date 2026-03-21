package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.LanguagesRequest;
import com.charter.tech.keycloakopa.dto.LanguagesResponse;
import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.entity.Languages;
import com.charter.tech.keycloakopa.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LanguagesMapper extends BaseMapper<Languages, LanguagesRequest, LanguagesResponse> {

    LanguagesMapper INSTANCE = Mappers.getMapper(LanguagesMapper.class);

}