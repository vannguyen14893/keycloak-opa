package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import com.charter.tech.keycloakopa.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/role")
public class RoleController extends BaseController {
    private final RoleService roleService;
    @Operation(summary = "messages.get.roles.summary", description = "messages.get.roles.desc")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<RoleResponse>>> getListRole() {
        return execute(roleService.findAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<RoleResponse>> findRoleById(@PathVariable Long id) {
        return execute(roleService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(roleService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<RoleResponse>> create(@RequestBody @Valid RoleRequest roleRequest) {
        return execute(roleService.create(roleRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<RoleResponse>> update(@PathVariable Long id, @RequestBody @Valid RoleRequest roleRequest) {
        return execute(roleService.update(id, roleRequest));
    }
}
