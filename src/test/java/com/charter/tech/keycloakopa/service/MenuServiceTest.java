package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.MenuCrudResponse;
import com.charter.tech.keycloakopa.dto.MenuRequest;
import com.charter.tech.keycloakopa.dto.MenuResponse;
import com.charter.tech.keycloakopa.entity.Menu;
import com.charter.tech.keycloakopa.mappper.MenuMapper;
import com.charter.tech.keycloakopa.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuMapper menuMapper;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private MenuService menuService;

    private Menu menu;
    private MenuRequest menuRequest;
    private MenuCrudResponse menuCrudResponse;

    @BeforeEach
    void setUp() {
        menu = new Menu(1L, 0L, "/dashboard", "icon", 1);
        menuRequest = new MenuRequest(0L, "/dashboard", "icon", 1);
        menuCrudResponse = new MenuCrudResponse(1L, 0L, "/dashboard", "icon", 1);
    }

    @Test
    void findAll_shouldReturnListOfMenuCrudResponses() {
        // Given
        List<Menu> menuList = List.of(menu);
        when(menuRepository.findAll()).thenReturn(menuList);
        when(menuMapper.toResponse(menu)).thenReturn(menuCrudResponse);

        // When
        List<MenuCrudResponse> result = menuService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(menuCrudResponse, result.get(0));
        verify(menuRepository).findAll();
        verify(menuMapper).toResponse(menu);
    }

    @Test
    void findById_shouldReturnMenuCrudResponse_whenExists() {
        // Given
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(menuMapper.toResponse(menu)).thenReturn(menuCrudResponse);

        // When
        MenuCrudResponse result = menuService.findById(1L);

        // Then
        assertEquals(menuCrudResponse, result);
        verify(menuRepository).findById(1L);
        verify(menuMapper).toResponse(menu);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // Given
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> menuService.findById(1L));
        verify(menuRepository).findById(1L);
    }

    @Test
    void delete_shouldReturnId_whenDeleted() {
        // Given
        // No need to mock save since it's hard delete

        // When
        Long result = menuService.delete(1L);

        // Then
        assertEquals(1L, result);
        verify(menuRepository).deleteById(1L);
    }

    @Test
    void create_shouldReturnMenuCrudResponse_whenCreated() {
        // Given
        when(menuMapper.toEntityCreate(menuRequest)).thenReturn(menu);
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.toResponse(menu)).thenReturn(menuCrudResponse);

        // When
        MenuCrudResponse result = menuService.create(menuRequest);

        // Then
        assertEquals(menuCrudResponse, result);
        verify(menuMapper).toEntityCreate(menuRequest);
        verify(menuRepository).save(menu);
        verify(menuMapper).toResponse(menu);
    }

    @Test
    void update_shouldReturnMenuCrudResponse_whenUpdated() {
        // Given
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.toResponse(menu)).thenReturn(menuCrudResponse);

        // When
        MenuCrudResponse result = menuService.update(1L, menuRequest);

        // Then
        assertEquals(menuCrudResponse, result);
        verify(menuRepository).findById(1L);
        verify(menuMapper).toEntityUpdate(menuRequest, menu);
        verify(menuRepository).save(menu);
        verify(menuMapper).toResponse(menu);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Given
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> menuService.update(1L, menuRequest));
        verify(menuRepository).findById(1L);
    }

    @Test
    void convertToMenuCrudResponse_shouldReturnMenuCrudResponse() {
        // Given
        when(menuMapper.toResponse(menu)).thenReturn(menuCrudResponse);

        // When
        MenuCrudResponse result = menuService.convertToMenuCrudResponse(menu);

        // Then
        assertEquals(menuCrudResponse, result);
        verify(menuMapper).toResponse(menu);
    }

    @Test
    void getMenu_shouldReturnEmptyList_whenNoMenus() {
        // Given
        String locale = "en";
        when(httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).thenReturn(locale);
        when(menuRepository.findMenuByLocale(locale)).thenReturn(new ArrayList<>());

        // When
        List<MenuResponse> result = menuService.getMenu();

        // Then
        assertTrue(result.isEmpty());
        verify(httpServletRequest).getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        verify(menuRepository).findMenuByLocale(locale);
    }

    @Test
    void getMenu_shouldReturnRoots_whenOnlyRoots() {
        // Given
        String locale = "en";
        MenuResponse root1 = new MenuResponse(1L, null, "/dashboard", "icon1", "Dashboard");
        MenuResponse root2 = new MenuResponse(2L, null, "/users", "icon2", "Users");
        List<MenuResponse> flatList = List.of(root1, root2);
        when(httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).thenReturn(locale);
        when(menuRepository.findMenuByLocale(locale)).thenReturn(flatList);

        // When
        List<MenuResponse> result = menuService.getMenu();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(root1));
        assertTrue(result.contains(root2));
        verify(httpServletRequest).getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        verify(menuRepository).findMenuByLocale(locale);
    }

    @Test
    void getMenu_shouldBuildTree_whenParentAndChild() {
        // Given
        String locale = "en";
        MenuResponse parent = new MenuResponse(1L, null, "/dashboard", "icon1", "Dashboard");
        MenuResponse child = new MenuResponse(2L, 1L, "/dashboard/sub", "icon2", "Sub Dashboard");
        List<MenuResponse> flatList = List.of(parent, child);
        when(httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).thenReturn(locale);
        when(menuRepository.findMenuByLocale(locale)).thenReturn(flatList);

        // When
        List<MenuResponse> result = menuService.getMenu();

        // Then
        assertEquals(1, result.size());
        MenuResponse root = result.get(0);
        assertEquals(parent.getId(), root.getId());
        assertEquals(1, root.getChildren().size());
        assertEquals(child.getId(), root.getChildren().get(0).getId());
        verify(httpServletRequest).getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        verify(menuRepository).findMenuByLocale(locale);
    }

    @Test
    void getMenu_shouldHandleChildBeforeParent() {
        // Given
        String locale = "en";
        MenuResponse child = new MenuResponse(2L, 1L, "/dashboard/sub", "icon2", "Sub Dashboard");
        MenuResponse parent = new MenuResponse(1L, null, "/dashboard", "icon1", "Dashboard");
        List<MenuResponse> flatList = List.of(child, parent); // child before parent
        when(httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).thenReturn(locale);
        when(menuRepository.findMenuByLocale(locale)).thenReturn(flatList);

        // When
        List<MenuResponse> result = menuService.getMenu();

        // Then
        assertEquals(1, result.size());
        MenuResponse root = result.get(0);
        assertEquals(parent.getId(), root.getId());
        assertEquals(1, root.getChildren().size());
        assertEquals(child.getId(), root.getChildren().get(0).getId());
        verify(httpServletRequest).getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        verify(menuRepository).findMenuByLocale(locale);
    }
}
