package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.ScopesRequest;
import com.charter.tech.keycloakopa.dto.ScopesResponse;
import com.charter.tech.keycloakopa.entity.Scopes;
import com.charter.tech.keycloakopa.mappper.ScopesMapper;
import com.charter.tech.keycloakopa.repository.ScopesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScopesService {

    private final ScopesRepository scopesRepository;
    private final ScopesMapper scopesMapper;

    public List<ScopesResponse> findAll() {
        return scopesRepository.findAll().stream().map(this::convertToScopesResponse).toList();
    }

    public ScopesResponse findById(Long id) {
        return convertToScopesResponse(scopesRepository.findById(id).orElseThrow());
    }

    public Long delete(Long id) {
        scopesRepository.deleteById(id);
        return id;
    }

    public ScopesResponse create(ScopesRequest scopesRequest) {
        Scopes scopes = scopesMapper.toEntityCreate(scopesRequest);
        return convertToScopesResponse(scopesRepository.save(scopes));
    }

    public ScopesResponse update(Long id, ScopesRequest scopesRequest) {
        Scopes scopes = scopesRepository.findById(id).orElseThrow();
        scopesMapper.toEntityUpdate(scopesRequest, scopes);
        return convertToScopesResponse(scopesRepository.save(scopes));
    }

    public ScopesResponse convertToScopesResponse(Scopes scopes) {
        return scopesMapper.toResponse(scopes);
    }
}
