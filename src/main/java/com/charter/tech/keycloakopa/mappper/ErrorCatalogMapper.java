package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.ErrorCatalogRequest;
import com.charter.tech.keycloakopa.dto.ErrorCatalogResponse;
import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ErrorCatalogMapper extends BaseMapper<ErrorCatalog, ErrorCatalogRequest, ErrorCatalogResponse> {
    ErrorCatalogMapper INSTANCE = Mappers.getMapper(ErrorCatalogMapper.class);

}
