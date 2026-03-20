package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.service.SwaggerTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/swagger-translations")
@RequiredArgsConstructor
public class SwaggerTranslationController {
    private final SwaggerTranslationService swaggerTranslationService;

    @GetMapping
    public Map<String, String> getTranslations(@RequestParam(defaultValue = "vi") String lang) {
        return swaggerTranslationService.getByLocale(lang);
    }
}
