package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.constans.Constants;
import com.charter.tech.keycloakopa.dto.ErrorCatalogRequest;
import com.charter.tech.keycloakopa.dto.ErrorCatalogResponse;
import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import com.charter.tech.keycloakopa.entity.LanguagesDetail;
import com.charter.tech.keycloakopa.mappper.ErrorCatalogMapper;
import com.charter.tech.keycloakopa.repository.ErrorCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorCatalogService {

    private final ErrorCatalogRepository errorCatalogRepository;
    private final ErrorCatalogMapper errorCatalogMapper;

    @Transactional
    @Cacheable(value = "errorCatalog", cacheManager = "cacheManager")
    public Map<String, ErrorCatalog> findAllByService() {
        return errorCatalogRepository.findAllByService(Constants.USER_SERVICE).collect(Collectors.toMap(ErrorCatalog::getCode, Function.identity()));
    }

    public List<ErrorCatalogResponse> findAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return errorCatalogRepository.findAll(pageable).stream().map(this::convertToErrorCatalogResponse).toList();
    }

    public ErrorCatalogResponse findById(Long id) {
        return convertToErrorCatalogResponse(errorCatalogRepository.findById(id).orElseThrow());
    }

    public ErrorCatalogResponse create(ErrorCatalogRequest errorCatalogRequest) {
        ErrorCatalog errorCatalog = errorCatalogMapper.toEntityCreate(errorCatalogRequest);
        return convertToErrorCatalogResponse(errorCatalogRepository.save(errorCatalog));
    }

    public ErrorCatalogResponse update(Long id, ErrorCatalogRequest errorCatalogRequest) {
        ErrorCatalog errorCatalog = errorCatalogRepository.findById(id).orElseThrow();
        errorCatalogMapper.toEntityUpdate(errorCatalogRequest, errorCatalog);
        return convertToErrorCatalogResponse(errorCatalogRepository.save(errorCatalog));
    }

    public Long delete(Long id) {
        errorCatalogRepository.deleteById(id);
        return id;
    }

    public ErrorCatalogResponse convertToErrorCatalogResponse(ErrorCatalog errorCatalog) {
        return errorCatalogMapper.toResponse(errorCatalog);
    }
}
