package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.SwaggerTranslationRequest;
import com.charter.tech.keycloakopa.dto.SwaggerTranslationResponse;
import com.charter.tech.keycloakopa.entity.SwaggerTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SwaggerTranslationMapper extends BaseMapper<SwaggerTranslation, SwaggerTranslationRequest, SwaggerTranslationResponse> {

    SwaggerTranslationMapper INSTANCE = Mappers.getMapper(SwaggerTranslationMapper.class);

}
