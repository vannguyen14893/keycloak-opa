package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.*;
import com.charter.tech.keycloakopa.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/permission")
public class PermissionController extends BaseController {
    private final PermissionService permissionService;

    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<PermissionResponse>>> getListPermission() {
        return execute(permissionService.findAll());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PermissionResponse>> findPermissionById(@PathVariable Long id) {
        return execute(permissionService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(permissionService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<PermissionResponse>> create(@RequestBody @Valid PermissionRequest permissionRequest) {
        return execute(permissionService.create(permissionRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PermissionResponse>> update(@PathVariable Long id, @RequestBody @Valid PermissionRequest permissionRequest) {
        return execute(permissionService.update(id, permissionRequest));
    }
}
