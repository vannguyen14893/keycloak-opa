package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
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

    @OPAAuthorize(resource = "error-catalog", action = "read")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<ErrorCatalogResponse>>> getListErrorCatalog(@RequestParam int page, @RequestParam int size) {
        return execute(errorCatalogService.findAll(page, size));
    }

    @OPAAuthorize(resource = "error-catalog", action = "read")
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ErrorCatalogResponse>> findErrorCatalogById(@PathVariable Long id) {
        return execute(errorCatalogService.findById(id));
    }

    @OPAAuthorize(resource = "error-catalog", action = "delete")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(errorCatalogService.delete(id));
    }

    @OPAAuthorize(resource = "error-catalog", action = "create")
    @PostMapping()
    public ResponseEntity<SuccessResultResponse<ErrorCatalogResponse>> create(@RequestBody @Valid ErrorCatalogRequest errorCatalogRequest) {
        return execute(errorCatalogService.create(errorCatalogRequest));
    }

    @OPAAuthorize(resource = "error-catalog", action = "update")
    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ErrorCatalogResponse>> update(@PathVariable Long id, @RequestBody @Valid ErrorCatalogRequest errorCatalogRequest) {
        return execute(errorCatalogService.update(id, errorCatalogRequest));
    }
}
