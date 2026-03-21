package com.charter.tech.keycloakopa.mappper;

import com.charter.tech.keycloakopa.dto.MenuCrudResponse;
import com.charter.tech.keycloakopa.dto.MenuRequest;
import com.charter.tech.keycloakopa.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MenuMapper extends BaseMapper<Menu, MenuRequest, MenuCrudResponse> {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

}
