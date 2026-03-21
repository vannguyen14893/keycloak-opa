package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.PiiRuleRequest;
import com.charter.tech.keycloakopa.dto.PiiRuleResponse;
import com.charter.tech.keycloakopa.service.PiiRuleService;
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
class PiiRuleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PiiRuleService piiRuleService;

    @Spy
    @InjectMocks
    private PiiRuleController piiRuleController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private PiiRuleResponse piiRuleResponse;
    private PiiRuleRequest piiRuleRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(piiRuleController).build();
        piiRuleResponse = new PiiRuleResponse(1L, "email", "***", true);
        piiRuleRequest = new PiiRuleRequest("email", "***", true);

        // Mock the BaseController fields
        ReflectionTestUtils.setField(piiRuleController, "dbMessageSourceConfig", mock(DBMessageSourceConfig.class));
        ReflectionTestUtils.setField(piiRuleController, "httpServletRequest", mock(HttpServletRequest.class));

        lenient().when(((DBMessageSourceConfig) ReflectionTestUtils.getField(piiRuleController, "dbMessageSourceConfig")).getMessages(anyString(), any(), any(Locale.class))).thenReturn("message.success");
        lenient().when(((HttpServletRequest) ReflectionTestUtils.getField(piiRuleController, "httpServletRequest")).getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void getListPiiRule_shouldReturnListOfPiiRules() throws Exception {
        // Given
        List<PiiRuleResponse> piiRules = List.of(piiRuleResponse);
        when(piiRuleService.findAllPiiRule()).thenReturn(piiRules);

        // When & Then
        mockMvc.perform(get("/v1/pii-rule"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].key").value("email"))
                .andExpect(jsonPath("$.data[0].masked").value("***"))
                .andExpect(jsonPath("$.data[0].status").value(true));
    }

    @Test
    void findPiiRuleById_shouldReturnPiiRule() throws Exception {
        // Given
        when(piiRuleService.findById(1L)).thenReturn(piiRuleResponse);

        // When & Then
        mockMvc.perform(get("/v1/pii-rule/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.key").value("email"))
                .andExpect(jsonPath("$.data.masked").value("***"))
                .andExpect(jsonPath("$.data.status").value(true));
    }

    @Test
    void delete_shouldReturnDeletedId() throws Exception {
        // Given
        when(piiRuleService.delete(1L)).thenReturn(1L);

        // When & Then
        mockMvc.perform(delete("/v1/pii-rule/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void create_shouldReturnCreatedPiiRule() throws Exception {
        // Given
        when(piiRuleService.create(any(PiiRuleRequest.class))).thenReturn(piiRuleResponse);

        // When & Then
        mockMvc.perform(post("/v1/pii-rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(piiRuleRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.key").value("email"))
                .andExpect(jsonPath("$.data.masked").value("***"))
                .andExpect(jsonPath("$.data.status").value(true));
    }

    @Test
    void create_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        PiiRuleRequest invalidRequest = new PiiRuleRequest("", "", null); // Invalid as per @NotBlank

        // When & Then
        mockMvc.perform(post("/v1/pii-rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_shouldReturnUpdatedPiiRule() throws Exception {
        // Given
        when(piiRuleService.update(eq(1L), any(PiiRuleRequest.class))).thenReturn(piiRuleResponse);

        // When & Then
        mockMvc.perform(put("/v1/pii-rule/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(piiRuleRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.key").value("email"))
                .andExpect(jsonPath("$.data.masked").value("***"))
                .andExpect(jsonPath("$.data.status").value(true));
    }

    @Test
    void update_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Given
        PiiRuleRequest invalidRequest = new PiiRuleRequest("", "", null);

        // When & Then
        mockMvc.perform(put("/v1/pii-rule/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
