package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.ErrorCatalogRequest;
import com.charter.tech.keycloakopa.dto.ErrorCatalogResponse;
import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ErrorCatalogMapper {
    ErrorCatalogMapper INSTANCE = Mappers.getMapper(ErrorCatalogMapper.class);

    ErrorCatalogResponse toResponse(ErrorCatalog errorCatalog);

    ErrorCatalog toEntityCreate(ErrorCatalogRequest errorCatalogRequest);

    void toEntityUpdate(ErrorCatalogRequest errorCatalogRequest, @MappingTarget ErrorCatalog errorCatalog);
}
