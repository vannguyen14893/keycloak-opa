package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.LanguagesRequest;
import com.charter.tech.keycloakopa.dto.LanguagesResponse;
import com.charter.tech.keycloakopa.service.LanguagesService;
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
class LanguagesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LanguagesService languagesService;

    @Spy
    @InjectMocks
    private LanguagesController languagesController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private LanguagesResponse languagesResponse;
    private LanguagesRequest languagesRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(languagesController).build();
        languagesResponse = new LanguagesResponse(1L, "EN", "English", true);
        languagesRequest = new LanguagesRequest("EN", "English", true);

        // Mock the BaseController fields
        ReflectionTestUtils.setField(languagesController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(languagesController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(languagesController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(languagesController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListLanguages_shouldReturnListOfLanguages() throws Exception {
        // Given
        List<LanguagesResponse> languages = List.of(languagesResponse);
        when(languagesService.findAll()).thenReturn(languages);

        // When & Then
        mockMvc.perform(get("/v1/languages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].code").value("EN"))
                .andExpect(jsonPath("$.data[0].name").value("English"))
                .andExpect(jsonPath("$.data[0].status").value(true));
    }

    @Test
    void findLanguagesById_shouldReturnLanguage() throws Exception {
        // Given
        when(languagesService.findById(1L)).thenReturn(languagesResponse);

        // When & Then
        mockMvc.perform(get("/v1/languages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("EN"))
                .andExpect(jsonPath("$.data.name").value("English"))
                .andExpect(jsonPath("$.data.status").value(true));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(languagesService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/languages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedLanguage() throws Exception {
        // Given
        when(languagesService.create(any(LanguagesRequest.class))).thenReturn(languagesResponse);

        // When & Then
        mockMvc.perform(post("/v1/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(languagesRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("EN"))
                .andExpect(jsonPath("$.data.name").value("English"))
                .andExpect(jsonPath("$.data.status").value(true));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        LanguagesRequest invalidRequest = new LanguagesRequest("", "", null); // Invalid as per @NotBlank

        // When & Then
        mockMvc.perform(post("/v1/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedLanguage() throws Exception {
        // Given
        when(languagesService.update(eq(1L), any(LanguagesRequest.class))).thenReturn(languagesResponse);

        // When & Then
        mockMvc.perform(put("/v1/languages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(languagesRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.code").value("EN"))
                .andExpect(jsonPath("$.data.name").value("English"))
                .andExpect(jsonPath("$.data.status").value(true));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        LanguagesRequest invalidRequest = new LanguagesRequest("", "", null);

        // When & Then
        mockMvc.perform(put("/v1/languages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
