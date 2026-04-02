package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import com.charter.tech.keycloakopa.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/role")
public class RoleController extends BaseController {

    private final RoleService roleService;

    //@OPAAuthorize(resource = "role", action = "read")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<RoleResponse>>> getListRole() {
        return execute(roleService.findAll());
    }

    @OPAAuthorize(resource = "role", action = "read")
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<RoleResponse>> findRoleById(@PathVariable Long id) {
        return execute(roleService.findById(id));
    }
    @OPAAuthorize(resource = "role", action = "delete")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(roleService.delete(id));
    }

    @OPAAuthorize(resource = "role", action = "create")
    @PostMapping()
    public ResponseEntity<SuccessResultResponse<RoleResponse>> create(@RequestBody @Valid RoleRequest roleRequest) {
        return execute(roleService.create(roleRequest));
    }

    @OPAAuthorize(resource = "role", action = "update")
    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<RoleResponse>> update(@PathVariable Long id, @RequestBody @Valid RoleRequest roleRequest) {
        return execute(roleService.update(id, roleRequest));
    }
}
