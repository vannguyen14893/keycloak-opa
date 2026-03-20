package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.PiiRuleRequest;
import com.charter.tech.keycloakopa.dto.PiiRuleResponse;
import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.entity.PiiRule;
import com.charter.tech.keycloakopa.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PiiRuleMapper {

    PiiRuleMapper INSTANCE = Mappers.getMapper(PiiRuleMapper.class);

    PiiRuleResponse toResponse(PiiRule piiRule);

    PiiRule toEntityCreate(PiiRuleRequest piiRuleRequest);

    void toEntityUpdate(PiiRuleRequest piiRuleRequest, @MappingTarget PiiRule piiRule);
}