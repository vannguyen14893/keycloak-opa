package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
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

    @OPAAuthorize(resource = "scopes", action = "read")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<ScopesResponse>>> getListScopes() {
        return execute(scopesService.findAll());
    }

    @OPAAuthorize(resource = "scopes", action = "read")
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ScopesResponse>> findScopesById(@PathVariable Long id) {
        return execute(scopesService.findById(id));
    }
    @OPAAuthorize(resource = "scopes", action = "delete")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(scopesService.delete(id));
    }
    @OPAAuthorize(resource = "scopes", action = "create")
    @PostMapping()
    public ResponseEntity<SuccessResultResponse<ScopesResponse>> create(@RequestBody @Valid ScopesRequest scopesRequest) {
        return execute(scopesService.create(scopesRequest));
    }
    @OPAAuthorize(resource = "scopes", action = "update")
    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<ScopesResponse>> update(@PathVariable Long id, @RequestBody @Valid ScopesRequest scopesRequest) {
        return execute(scopesService.update(id, scopesRequest));
    }
}
