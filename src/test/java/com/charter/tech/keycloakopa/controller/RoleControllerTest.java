package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.service.RoleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @Spy
    @InjectMocks
    private RoleController roleController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private RoleResponse roleResponse;
    private RoleRequest roleRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
        roleResponse = new RoleResponse(1L, "ADMIN", "Administrator");
        roleRequest = new RoleRequest("ADMIN", "Administrator");

        // Mock the BaseController fields
        ReflectionTestUtils.setField(roleController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(roleController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(roleController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(roleController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListRole_shouldReturnListOfRoles() throws Exception {
        // Given
        List<RoleResponse> roles = List.of(roleResponse);
        when(roleService.findAll()).thenReturn(roles);

        // When & Then
        mockMvc.perform(get("/v1/role"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].code").value("ADMIN"))
                .andExpect(jsonPath("$.data[0].name").value("Administrator"));
    }

    @Test
    void findRoleById_shouldReturnRole() throws Exception {
        // Given
        when(roleService.findById(1L)).thenReturn(roleResponse);

        // When & Then
        mockMvc.perform(get("/v1/role/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("ADMIN"))
                .andExpect(jsonPath("$.data.name").value("Administrator"));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(roleService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/role/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedRole() throws Exception {
        // Given
        when(roleService.create(any(RoleRequest.class))).thenReturn(roleResponse);

        // When & Then
        mockMvc.perform(post("/v1/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("ADMIN"))
                .andExpect(jsonPath("$.data.name").value("Administrator"));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        RoleRequest invalidRequest = new RoleRequest("", ""); // Invalid as per @NotBlank

        // When & Then
        mockMvc.perform(post("/v1/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedRole() throws Exception {
        // Given
        when(roleService.update(eq(1L), any(RoleRequest.class))).thenReturn(roleResponse);

        // When & Then
        mockMvc.perform(put("/v1/role/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("ADMIN"))
                .andExpect(jsonPath("$.data.name").value("Administrator"));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        RoleRequest invalidRequest = new RoleRequest("", "");

        // When & Then
        mockMvc.perform(put("/v1/role/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
