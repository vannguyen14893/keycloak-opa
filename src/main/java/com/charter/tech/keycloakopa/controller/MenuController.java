package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.MenuResponse;
import com.charter.tech.keycloakopa.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuResponse> getMenu() {
        return menuService.getMenu();
    }
}
