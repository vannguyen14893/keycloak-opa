package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.LanguagesRequest;
import com.charter.tech.keycloakopa.dto.LanguagesResponse;
import com.charter.tech.keycloakopa.entity.Languages;
import com.charter.tech.keycloakopa.mappper.LanguagesMapper;
import com.charter.tech.keycloakopa.repository.LanguagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguagesService {

    private final LanguagesRepository languagesRepository;
    private final LanguagesMapper languagesMapper;

    public List<LanguagesResponse> findAll() {
        return languagesRepository.findAll().stream().map(this::convertToLanguagesResponse).toList();
    }

    public LanguagesResponse findById(Long id) {
        return convertToLanguagesResponse(languagesRepository.findById(id).orElseThrow());
    }

    public Long delete(Long id) {
        Languages languages = languagesRepository.findById(id).orElseThrow();
        languages.setStatus(false);
        languagesRepository.save(languages);
        return id;
    }

    public LanguagesResponse create(LanguagesRequest languagesRequest) {
        Languages languages = languagesMapper.toEntityCreate(languagesRequest);
        return convertToLanguagesResponse(languagesRepository.save(languages));
    }

    public LanguagesResponse update(Long id, LanguagesRequest languagesRequest) {
        Languages languages = languagesRepository.findById(id).orElseThrow();
        languagesMapper.toEntityUpdate(languagesRequest, languages);
        return convertToLanguagesResponse(languagesRepository.save(languages));
    }

    public LanguagesResponse convertToLanguagesResponse(Languages languages) {
        return languagesMapper.toResponse(languages);
    }
}
