package com.charter.tech.keycloakopa.mappper;

import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

/**
 * @param <E> Entity type
 * @param <R> Request DTO type (for Create/Update)
 * @param <S> Response DTO type
 */
public interface BaseMapper<E, R, S> {

    S toResponse(E entity);

    E toEntityCreate(R request);

    void toEntityUpdate(R request, @MappingTarget E entity);
}
