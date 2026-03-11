package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.entity.Languages;
import com.charter.tech.keycloakopa.repository.LanguagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguagesService {

    private final LanguagesRepository languagesRepository;

    public List<Languages> findAll() {
        return languagesRepository.findAll();
    }
}
