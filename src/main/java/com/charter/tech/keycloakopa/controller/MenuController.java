package com.charter.tech.keycloakopa.controller;

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

    @GetMapping("/tree")
    public List<MenuResponse> getMenu() {
        return menuService.getMenu();
    }

    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<MenuCrudResponse>>> getListMenu() {
        return execute(menuService.findAll());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<MenuCrudResponse>> findMenuById(@PathVariable Long id) {
        return execute(menuService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(menuService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<MenuCrudResponse>> create(@RequestBody @Valid MenuRequest menuRequest) {
        return execute(menuService.create(menuRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<MenuCrudResponse>> update(@PathVariable Long id, @RequestBody @Valid MenuRequest menuRequest) {
        return execute(menuService.update(id, menuRequest));
    }
}
