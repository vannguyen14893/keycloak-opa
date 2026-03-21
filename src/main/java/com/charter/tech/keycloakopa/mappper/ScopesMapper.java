package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.ScopesRequest;
import com.charter.tech.keycloakopa.dto.ScopesResponse;
import com.charter.tech.keycloakopa.entity.Scopes;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ScopesMapper extends BaseMapper<Scopes, ScopesRequest, ScopesResponse> {

    ScopesMapper INSTANCE = Mappers.getMapper(ScopesMapper.class);

}
