package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.constans.Constants;
import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import com.charter.tech.keycloakopa.entity.LanguagesDetail;
import com.charter.tech.keycloakopa.repository.ErrorCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ErrorCatalogService {

    private final ErrorCatalogRepository errorCatalogRepository;

    @Transactional
    @Cacheable(value = "errorCatalog", cacheManager = "cacheManager")
    public Map<String, ErrorCatalog> findAllByService() {
        return errorCatalogRepository.findAllByService(Constants.USER_SERVICE).collect(Collectors.toMap(ErrorCatalog::getCode, Function.identity()));
    }
}
