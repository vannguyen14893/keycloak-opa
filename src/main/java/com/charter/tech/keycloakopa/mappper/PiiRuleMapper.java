package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.PiiRuleRequest;
import com.charter.tech.keycloakopa.dto.PiiRuleResponse;
import com.charter.tech.keycloakopa.entity.PiiRule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PiiRuleMapper extends BaseMapper<PiiRule, PiiRuleRequest, PiiRuleResponse> {

    PiiRuleMapper INSTANCE = Mappers.getMapper(PiiRuleMapper.class);

}