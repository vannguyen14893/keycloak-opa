package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.ErrorCatalogRequest;
import com.charter.tech.keycloakopa.dto.ErrorCatalogResponse;
import com.charter.tech.keycloakopa.service.ErrorCatalogService;
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
class ErrorCatalogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ErrorCatalogService errorCatalogService;

    @Spy
    @InjectMocks
    private ErrorCatalogController errorCatalogController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ErrorCatalogResponse errorCatalogResponse;
    private ErrorCatalogRequest errorCatalogRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(errorCatalogController).build();
        errorCatalogResponse = new ErrorCatalogResponse(1L, "ERR001", "VALIDATION", 400, "error.validation", "USER-SERVICE", "ERROR", false);
        errorCatalogRequest = new ErrorCatalogRequest("ERR001", "VALIDATION", 400, "error.validation", "USER-SERVICE", "ERROR", false);

        // Mock the BaseController fields
        ReflectionTestUtils.setField(errorCatalogController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(errorCatalogController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(errorCatalogController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(errorCatalogController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListErrorCatalog_shouldReturnListOfErrorCatalogs() throws Exception {
        // Given
        List<ErrorCatalogResponse> errorCatalogs = List.of(errorCatalogResponse);
        when(errorCatalogService.findAll(0, 10)).thenReturn(errorCatalogs);

        // When & Then
        mockMvc.perform(get("/v1/error-catalog")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].code").value("ERR001"))
                .andExpect(jsonPath("$.data[0].type").value("VALIDATION"))
                .andExpect(jsonPath("$.data[0].httpStatus").value(400))
                .andExpect(jsonPath("$.data[0].messageKey").value("error.validation"))
                .andExpect(jsonPath("$.data[0].service").value("USER-SERVICE"))
                .andExpect(jsonPath("$.data[0].severity").value("ERROR"))
                .andExpect(jsonPath("$.data[0].retryable").value(false));
    }

    @Test
    void findErrorCatalogById_shouldReturnErrorCatalog() throws Exception {
        // Given
        when(errorCatalogService.findById(1L)).thenReturn(errorCatalogResponse);

        // When & Then
        mockMvc.perform(get("/v1/error-catalog/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("ERR001"))
                .andExpect(jsonPath("$.data.type").value("VALIDATION"))
                .andExpect(jsonPath("$.data.httpStatus").value(400))
                .andExpect(jsonPath("$.data.messageKey").value("error.validation"))
                .andExpect(jsonPath("$.data.service").value("USER-SERVICE"))
                .andExpect(jsonPath("$.data.severity").value("ERROR"))
                .andExpect(jsonPath("$.data.retryable").value(false));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(errorCatalogService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/error-catalog/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedErrorCatalog() throws Exception {
        // Given
        when(errorCatalogService.create(any(ErrorCatalogRequest.class))).thenReturn(errorCatalogResponse);

        // When & Then
        mockMvc.perform(post("/v1/error-catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(errorCatalogRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("ERR001"))
                .andExpect(jsonPath("$.data.type").value("VALIDATION"))
                .andExpect(jsonPath("$.data.httpStatus").value(400))
                .andExpect(jsonPath("$.data.messageKey").value("error.validation"))
                .andExpect(jsonPath("$.data.service").value("USER-SERVICE"))
                .andExpect(jsonPath("$.data.severity").value("ERROR"))
                .andExpect(jsonPath("$.data.retryable").value(false));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        ErrorCatalogRequest invalidRequest = new ErrorCatalogRequest("", "", null, "", "", "", null); // Invalid as per @NotBlank and @NotNull

        // When & Then
        mockMvc.perform(post("/v1/error-catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedErrorCatalog() throws Exception {
        // Given
        when(errorCatalogService.update(eq(1L), any(ErrorCatalogRequest.class))).thenReturn(errorCatalogResponse);

        // When & Then
        mockMvc.perform(put("/v1/error-catalog/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(errorCatalogRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("ERR001"))
                .andExpect(jsonPath("$.data.type").value("VALIDATION"))
                .andExpect(jsonPath("$.data.httpStatus").value(400))
                .andExpect(jsonPath("$.data.messageKey").value("error.validation"))
                .andExpect(jsonPath("$.data.service").value("USER-SERVICE"))
                .andExpect(jsonPath("$.data.severity").value("ERROR"))
                .andExpect(jsonPath("$.data.retryable").value(false));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        ErrorCatalogRequest invalidRequest = new ErrorCatalogRequest("", "", null, "", "", "", null);

        // When & Then
        mockMvc.perform(put("/v1/error-catalog/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
