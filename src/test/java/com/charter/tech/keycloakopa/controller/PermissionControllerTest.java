package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.PermissionRequest;
import com.charter.tech.keycloakopa.dto.PermissionResponse;
import com.charter.tech.keycloakopa.service.PermissionService;
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
class PermissionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PermissionService permissionService;

    @Spy
    @InjectMocks
    private PermissionController permissionController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private PermissionResponse permissionResponse;
    private PermissionRequest permissionRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
        permissionResponse = new PermissionResponse(1L, "READ_USER", "Read user permission");
        permissionRequest = new PermissionRequest("READ_USER", "Read user permission");

        // Mock the BaseController fields
        ReflectionTestUtils.setField(permissionController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(permissionController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(permissionController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(permissionController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListPermission_shouldReturnListOfPermissions() throws Exception {
        // Given
        List<PermissionResponse> permissions = List.of(permissionResponse);
        when(permissionService.findAll()).thenReturn(permissions);

        // When & Then
        mockMvc.perform(get("/v1/permission"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("READ_USER"))
                .andExpect(jsonPath("$.data[0].description").value("Read user permission"));
    }

    @Test
    void findPermissionById_shouldReturnPermission() throws Exception {
        // Given
        when(permissionService.findById(1L)).thenReturn(permissionResponse);

        // When & Then
        mockMvc.perform(get("/v1/permission/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("READ_USER"))
                .andExpect(jsonPath("$.data.description").value("Read user permission"));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(permissionService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/permission/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedPermission() throws Exception {
        // Given
        when(permissionService.create(any(PermissionRequest.class))).thenReturn(permissionResponse);

        // When & Then
        mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("READ_USER"))
                .andExpect(jsonPath("$.data.description").value("Read user permission"));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        PermissionRequest invalidRequest = new PermissionRequest("", ""); // Invalid as per @NotBlank

        // When & Then
        mockMvc.perform(post("/v1/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedPermission() throws Exception {
        // Given
        when(permissionService.update(eq(1L), any(PermissionRequest.class))).thenReturn(permissionResponse);

        // When & Then
        mockMvc.perform(put("/v1/permission/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("READ_USER"))
                .andExpect(jsonPath("$.data.description").value("Read user permission"));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        PermissionRequest invalidRequest = new PermissionRequest("", "");

        // When & Then
        mockMvc.perform(put("/v1/permission/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
