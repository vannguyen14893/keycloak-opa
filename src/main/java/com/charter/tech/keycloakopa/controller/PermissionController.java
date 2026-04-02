package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
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

    @OPAAuthorize(resource = "permission", action = "read")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<PermissionResponse>>> getListPermission() {
        return execute(permissionService.findAll());
    }

    @OPAAuthorize(resource = "permission", action = "read")
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PermissionResponse>> findPermissionById(@PathVariable Long id) {
        return execute(permissionService.findById(id));
    }

    @OPAAuthorize(resource = "permission", action = "delete")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(permissionService.delete(id));
    }

    @OPAAuthorize(resource = "permission", action = "create")
    @PostMapping()
    public ResponseEntity<SuccessResultResponse<PermissionResponse>> create(@RequestBody @Valid PermissionRequest permissionRequest) {
        return execute(permissionService.create(permissionRequest));
    }

    @OPAAuthorize(resource = "permission", action = "update")
    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PermissionResponse>> update(@PathVariable Long id, @RequestBody @Valid PermissionRequest permissionRequest) {
        return execute(permissionService.update(id, permissionRequest));
    }
}
