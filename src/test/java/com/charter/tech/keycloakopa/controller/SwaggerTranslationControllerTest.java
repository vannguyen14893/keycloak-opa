package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.SwaggerTranslationRequest;
import com.charter.tech.keycloakopa.dto.SwaggerTranslationResponse;
import com.charter.tech.keycloakopa.service.SwaggerTranslationService;
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
class SwaggerTranslationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SwaggerTranslationService swaggerTranslationService;

    @Spy
    @InjectMocks
    private SwaggerTranslationController swaggerTranslationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private SwaggerTranslationResponse swaggerTranslationResponse;
    private SwaggerTranslationRequest swaggerTranslationRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(swaggerTranslationController).build();
        swaggerTranslationResponse = new SwaggerTranslationResponse(1L, "user.create", "en", "Create User", "Creates a new user");
        swaggerTranslationRequest = new SwaggerTranslationRequest("user.create", "en", "Create User", "Creates a new user");

        // Mock the BaseController fields
        ReflectionTestUtils.setField(swaggerTranslationController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(swaggerTranslationController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(swaggerTranslationController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(swaggerTranslationController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListSwaggerTranslation_shouldReturnListOfSwaggerTranslations() throws Exception {
        // Given
        List<SwaggerTranslationResponse> swaggerTranslations = List.of(swaggerTranslationResponse);
        when(swaggerTranslationService.findAll()).thenReturn(swaggerTranslations);

        // When & Then
        mockMvc.perform(get("/v1/swagger-translation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].key").value("user.create"))
                .andExpect(jsonPath("$.data[0].locale").value("en"))
                .andExpect(jsonPath("$.data[0].name").value("Create User"))
                .andExpect(jsonPath("$.data[0].description").value("Creates a new user"));
    }

    @Test
    void findSwaggerTranslationById_shouldReturnSwaggerTranslation() throws Exception {
        // Given
        when(swaggerTranslationService.findById(1L)).thenReturn(swaggerTranslationResponse);

        // When & Then
        mockMvc.perform(get("/v1/swagger-translation/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.key").value("user.create"))
                .andExpect(jsonPath("$.data.locale").value("en"))
                .andExpect(jsonPath("$.data.name").value("Create User"))
                .andExpect(jsonPath("$.data.description").value("Creates a new user"));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(swaggerTranslationService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/swagger-translation/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedSwaggerTranslation() throws Exception {
        // Given
        when(swaggerTranslationService.create(any(SwaggerTranslationRequest.class))).thenReturn(swaggerTranslationResponse);

        // When & Then
        mockMvc.perform(post("/v1/swagger-translation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(swaggerTranslationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.key").value("user.create"))
                .andExpect(jsonPath("$.data.locale").value("en"))
                .andExpect(jsonPath("$.data.name").value("Create User"))
                .andExpect(jsonPath("$.data.description").value("Creates a new user"));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        SwaggerTranslationRequest invalidRequest = new SwaggerTranslationRequest("", "", "", ""); // Invalid as per @NotBlank

        // When & Then
        mockMvc.perform(post("/v1/swagger-translation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedSwaggerTranslation() throws Exception {
        // Given
        when(swaggerTranslationService.update(eq(1L), any(SwaggerTranslationRequest.class))).thenReturn(swaggerTranslationResponse);

        // When & Then
        mockMvc.perform(put("/v1/swagger-translation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(swaggerTranslationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.key").value("user.create"))
                .andExpect(jsonPath("$.data.locale").value("en"))
                .andExpect(jsonPath("$.data.name").value("Create User"))
                .andExpect(jsonPath("$.data.description").value("Creates a new user"));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        SwaggerTranslationRequest invalidRequest = new SwaggerTranslationRequest("", "", "", "");

        // When & Then
        mockMvc.perform(put("/v1/swagger-translation/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
