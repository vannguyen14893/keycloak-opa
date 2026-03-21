package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.MenuCrudResponse;
import com.charter.tech.keycloakopa.dto.MenuRequest;
import com.charter.tech.keycloakopa.dto.MenuResponse;
import com.charter.tech.keycloakopa.entity.Menu;
import com.charter.tech.keycloakopa.mappper.MenuMapper;
import com.charter.tech.keycloakopa.repository.MenuRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final HttpServletRequest httpServletRequest;

    @Cacheable(value = "menu", cacheManager = "cacheManager")
    public List<MenuResponse> getMenu() {
        String locale = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        List<MenuResponse> flatList = menuRepository.findMenuByLocale(locale);
        Map<Long, MenuResponse> nodeMap = new HashMap<>(flatList.size());
        Map<Long, List<MenuResponse>> waitingChildren = new HashMap<>();
        List<MenuResponse> roots = new ArrayList<>();
        for (MenuResponse node : flatList) {
            nodeMap.put(node.getId(), node);
            Long parentId = node.getParentId();
            if (parentId == null) {
                roots.add(node);
            } else {
                MenuResponse parent = nodeMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    waitingChildren
                            .computeIfAbsent(parentId, k -> new ArrayList<>())
                            .add(node);
                }
            }
            List<MenuResponse> children = waitingChildren.remove(node.getId());
            if (children != null) {
                node.getChildren().addAll(children);
            }
        }
        return roots;
    }

    public List<MenuCrudResponse> findAll() {
        return menuRepository.findAll().stream().map(this::convertToMenuCrudResponse).toList();
    }

    public MenuCrudResponse findById(Long id) {
        return convertToMenuCrudResponse(menuRepository.findById(id).orElseThrow());
    }

    public Long delete(Long id) {
        menuRepository.deleteById(id);
        return id;
    }

    public MenuCrudResponse create(MenuRequest menuRequest) {
        Menu menu = menuMapper.toEntityCreate(menuRequest);
        return convertToMenuCrudResponse(menuRepository.save(menu));
    }

    public MenuCrudResponse update(Long id, MenuRequest menuRequest) {
        Menu menu = menuRepository.findById(id).orElseThrow();
        menuMapper.toEntityUpdate(menuRequest, menu);
        return convertToMenuCrudResponse(menuRepository.save(menu));
    }

    public MenuCrudResponse convertToMenuCrudResponse(Menu menu) {
        return menuMapper.toResponse(menu);
    }
}
