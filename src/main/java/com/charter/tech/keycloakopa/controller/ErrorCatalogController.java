package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.*;
import com.charter.tech.keycloakopa.service.ErrorCatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/error-catalog")
public class ErrorCatalogController extends BaseController {
    private final ErrorCatalogService errorCatalogService;

    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<ErrorCatalogResponse>>> getListErrorCatalog(@RequestParam int page, @RequestParam int size) {
        return execute(errorCatalogService.findAll(page, size));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ErrorCatalogResponse>> findErrorCatalogById(@PathVariable Long id) {
        return execute(errorCatalogService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(errorCatalogService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<ErrorCatalogResponse>> create(@RequestBody @Valid ErrorCatalogRequest errorCatalogRequest) {
        return execute(errorCatalogService.create(errorCatalogRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ErrorCatalogResponse>> update(@PathVariable Long id, @RequestBody @Valid ErrorCatalogRequest errorCatalogRequest) {
        return execute(errorCatalogService.update(id, errorCatalogRequest));
    }
}
