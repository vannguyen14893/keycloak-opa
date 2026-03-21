package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.ScopesRequest;
import com.charter.tech.keycloakopa.dto.ScopesResponse;
import com.charter.tech.keycloakopa.service.ScopesService;
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
class ScopesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScopesService scopesService;

    @Spy
    @InjectMocks
    private ScopesController scopesController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ScopesResponse scopesResponse;
    private ScopesRequest scopesRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(scopesController).build();
        scopesResponse = new ScopesResponse(1L, "READ", "Read scope");
        scopesRequest = new ScopesRequest("READ", "Read scope");

        // Mock the BaseController fields
        ReflectionTestUtils.setField(scopesController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(scopesController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(scopesController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(scopesController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListScopes_shouldReturnListOfScopes() throws Exception {
        // Given
        List<ScopesResponse> scopes = List.of(scopesResponse);
        when(scopesService.findAll()).thenReturn(scopes);

        // When & Then
        mockMvc.perform(get("/v1/scopes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("READ"))
                .andExpect(jsonPath("$.data[0].description").value("Read scope"));
    }

    @Test
    void findScopesById_shouldReturnScope() throws Exception {
        // Given
        when(scopesService.findById(1L)).thenReturn(scopesResponse);

        // When & Then
        mockMvc.perform(get("/v1/scopes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("READ"))
                .andExpect(jsonPath("$.data.description").value("Read scope"));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(scopesService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/scopes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedScope() throws Exception {
        // Given
        when(scopesService.create(any(ScopesRequest.class))).thenReturn(scopesResponse);

        // When & Then
        mockMvc.perform(post("/v1/scopes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scopesRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("READ"))
                .andExpect(jsonPath("$.data.description").value("Read scope"));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        ScopesRequest invalidRequest = new ScopesRequest("", ""); // Invalid as per @NotBlank

        // When & Then
        mockMvc.perform(post("/v1/scopes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedScope() throws Exception {
        // Given
        when(scopesService.update(eq(1L), any(ScopesRequest.class))).thenReturn(scopesResponse);

        // When & Then
        mockMvc.perform(put("/v1/scopes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scopesRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("READ"))
                .andExpect(jsonPath("$.data.description").value("Read scope"));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        ScopesRequest invalidRequest = new ScopesRequest("", "");

        // When & Then
        mockMvc.perform(put("/v1/scopes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
