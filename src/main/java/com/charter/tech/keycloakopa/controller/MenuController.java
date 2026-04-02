package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
import com.charter.tech.keycloakopa.dto.*;
import com.charter.tech.keycloakopa.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/menu")
public class MenuController extends BaseController {

    private final MenuService menuService;

    @OPAAuthorize(resource = "menu", action = "read")
    @GetMapping("/tree")
    public List<MenuResponse> getMenu() {
        return menuService.getMenu();
    }

    @OPAAuthorize(resource = "menu", action = "read")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<MenuCrudResponse>>> getListMenu() {
        return execute(menuService.findAll());
    }

    @OPAAuthorize(resource = "menu", action = "read")
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<MenuCrudResponse>> findMenuById(@PathVariable Long id) {
        return execute(menuService.findById(id));
    }

    @OPAAuthorize(resource = "menu", action = "delete")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(menuService.delete(id));
    }

    @OPAAuthorize(resource = "menu", action = "create")
    @PostMapping()
    public ResponseEntity<SuccessResultResponse<MenuCrudResponse>> create(@RequestBody @Valid MenuRequest menuRequest) {
        return execute(menuService.create(menuRequest));
    }

    @OPAAuthorize(resource = "menu", action = "update")
    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<MenuCrudResponse>> update(@PathVariable Long id, @RequestBody @Valid MenuRequest menuRequest) {
        return execute(menuService.update(id, menuRequest));
    }
}
