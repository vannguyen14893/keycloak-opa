package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.MenuCrudResponse;
import com.charter.tech.keycloakopa.dto.MenuRequest;
import com.charter.tech.keycloakopa.dto.MenuResponse;
import com.charter.tech.keycloakopa.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MenuService menuService;

    @Spy
    @InjectMocks
    private MenuController menuController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MenuCrudResponse menuCrudResponse;
    private MenuRequest menuRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
        menuCrudResponse = new MenuCrudResponse(1L, 0L, "/dashboard", "icon", 1);
        menuRequest = new MenuRequest(0L, "/dashboard", "icon", 1);

        // Mock the BaseController fields
        ReflectionTestUtils.setField(menuController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(menuController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(menuController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(menuController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListMenu_shouldReturnListOfMenus() throws Exception {
        // Given
        List<MenuCrudResponse> menus = List.of(menuCrudResponse);
        when(menuService.findAll()).thenReturn(menus);

        // When & Then
        mockMvc.perform(get("/v1/menu"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].parentId").value(0))
                .andExpect(jsonPath("$.data[0].path").value("/dashboard"))
                .andExpect(jsonPath("$.data[0].icon").value("icon"))
                .andExpect(jsonPath("$.data[0].orderNo").value(1));
    }

    @Test
    void findMenuById_shouldReturnMenu() throws Exception {
        // Given
        when(menuService.findById(1L)).thenReturn(menuCrudResponse);

        // When & Then
        mockMvc.perform(get("/v1/menu/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.parentId").value(0))
                .andExpect(jsonPath("$.data.path").value("/dashboard"))
                .andExpect(jsonPath("$.data.icon").value("icon"))
                .andExpect(jsonPath("$.data.orderNo").value(1));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(menuService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/menu/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedMenu() throws Exception {
        // Given
        when(menuService.create(any(MenuRequest.class))).thenReturn(menuCrudResponse);

        // When & Then
        mockMvc.perform(post("/v1/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.parentId").value(0))
                .andExpect(jsonPath("$.data.path").value("/dashboard"))
                .andExpect(jsonPath("$.data.icon").value("icon"))
                .andExpect(jsonPath("$.data.orderNo").value(1));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        MenuRequest invalidRequest = new MenuRequest(null, "", "", null); // Invalid as per @NotNull and @NotBlank

        // When & Then
        mockMvc.perform(post("/v1/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedMenu() throws Exception {
        // Given
        when(menuService.update(eq(1L), any(MenuRequest.class))).thenReturn(menuCrudResponse);

        // When & Then
        mockMvc.perform(put("/v1/menu/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.parentId").value(0))
                .andExpect(jsonPath("$.data.path").value("/dashboard"))
                .andExpect(jsonPath("$.data.icon").value("icon"))
                .andExpect(jsonPath("$.data.orderNo").value(1));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        MenuRequest invalidRequest = new MenuRequest(null, "", "", null);

        // When & Then
        mockMvc.perform(put("/v1/menu/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMenuTree_shouldReturnMenuTree() throws Exception {
        // Given
        MenuResponse root = new MenuResponse(1L, null, "/dashboard", "icon1", "Dashboard");
        MenuResponse child = new MenuResponse(2L, 1L, "/dashboard/sub", "icon2", "Sub Dashboard");
        root.getChildren().add(child);
        List<MenuResponse> menuTree = List.of(root);
        when(menuService.getMenu()).thenReturn(menuTree);

        // When & Then
        mockMvc.perform(get("/v1/menu/tree"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].parentId").isEmpty())
                .andExpect(jsonPath("$[0].path").value("/dashboard"))
                .andExpect(jsonPath("$[0].icon").value("icon1"))
                .andExpect(jsonPath("$[0].name").value("Dashboard"))
                .andExpect(jsonPath("$[0].children[0].id").value(2))
                .andExpect(jsonPath("$[0].children[0].parentId").value(1))
                .andExpect(jsonPath("$[0].children[0].path").value("/dashboard/sub"))
                .andExpect(jsonPath("$[0].children[0].icon").value("icon2"))
                .andExpect(jsonPath("$[0].children[0].name").value("Sub Dashboard"));
    }
}
