package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.*;
import com.charter.tech.keycloakopa.service.LanguagesService;
import com.charter.tech.keycloakopa.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/languages")
public class LanguagesController extends BaseController {
    private final LanguagesService languagesService;

    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<LanguagesResponse>>> getListLanguages() {
        return execute(languagesService.findAll());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<LanguagesResponse>> findLanguagesById(@PathVariable Long id) {
        return execute(languagesService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(languagesService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<LanguagesResponse>> create(@RequestBody @Valid LanguagesRequest languagesRequest) {
        return execute(languagesService.create(languagesRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<LanguagesResponse>> update(@PathVariable Long id, @RequestBody @Valid LanguagesRequest languagesRequest) {
        return execute(languagesService.update(id, languagesRequest));
    }
}
