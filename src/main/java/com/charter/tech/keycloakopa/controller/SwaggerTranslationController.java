package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
import com.charter.tech.keycloakopa.dto.*;
import com.charter.tech.keycloakopa.service.SwaggerTranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/swagger-translation")
public class SwaggerTranslationController extends BaseController {
    private final SwaggerTranslationService swaggerTranslationService;

    @GetMapping("/locale")
    public Map<String, String> getTranslations(@RequestParam(defaultValue = "vi") String lang) {
        return swaggerTranslationService.getByLocale(lang);
    }
    @OPAAuthorize(resource = "swagger-translation", action = "read")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<SwaggerTranslationResponse>>> getListSwaggerTranslation() {
        return execute(swaggerTranslationService.findAll());
    }

    @OPAAuthorize(resource = "swagger-translation", action = "read")
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<SwaggerTranslationResponse>> findSwaggerTranslationById(@PathVariable Long id) {
        return execute(swaggerTranslationService.findById(id));
    }
    @OPAAuthorize(resource = "swagger-translation", action = "delete")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(swaggerTranslationService.delete(id));
    }
    @OPAAuthorize(resource = "swagger-translation", action = "create")
    @PostMapping()
    public ResponseEntity<SuccessResultResponse<SwaggerTranslationResponse>> create(@RequestBody @Valid SwaggerTranslationRequest swaggerTranslationRequest) {
        return execute(swaggerTranslationService.create(swaggerTranslationRequest));
    }
    @OPAAuthorize(resource = "swagger-translation", action = "update")
    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<SwaggerTranslationResponse>> update(@PathVariable Long id, @RequestBody @Valid SwaggerTranslationRequest swaggerTranslationRequest) {
        return execute(swaggerTranslationService.update(id, swaggerTranslationRequest));
    }
}
