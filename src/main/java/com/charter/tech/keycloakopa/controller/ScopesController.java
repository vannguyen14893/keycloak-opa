package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.*;
import com.charter.tech.keycloakopa.service.ScopesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/scopes")
public class ScopesController extends BaseController {
    private final ScopesService scopesService;

    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<ScopesResponse>>> getListScopes() {
        return execute(scopesService.findAll());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ScopesResponse>> findScopesById(@PathVariable Long id) {
        return execute(scopesService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(scopesService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<ScopesResponse>> create(@RequestBody @Valid ScopesRequest scopesRequest) {
        return execute(scopesService.create(scopesRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ScopesResponse>> update(@PathVariable Long id, @RequestBody @Valid ScopesRequest scopesRequest) {
        return execute(scopesService.update(id, scopesRequest));
    }
}
