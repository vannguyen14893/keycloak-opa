package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.constans.Constants;
import com.charter.tech.keycloakopa.entity.LanguagesDetail;
import com.charter.tech.keycloakopa.repository.LanguagesDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguagesDetailService {

    private final LanguagesDetailRepository languagesDetailRepository;

    @Transactional
    public Map<String, String> findAllByLanguagesCode(String languagesCode) {
        return languagesDetailRepository.findAllByLanguages_CodeAndLanguages_StatusIsTrue(languagesCode).collect(Collectors.toMap(LanguagesDetail::getKey, LanguagesDetail::getValue));
    }

    @Transactional
    @Cacheable(value = "languageDetails", cacheManager = "cacheManager")
    public Map<String, List<LanguagesDetail>> findAllByService() {
        return languagesDetailRepository.findAllByServiceAndLanguages_StatusIsTrue(Constants.USER_SERVICE)
                .collect(Collectors.groupingBy(detail -> detail.getLanguages().getCode()));

    }
}
