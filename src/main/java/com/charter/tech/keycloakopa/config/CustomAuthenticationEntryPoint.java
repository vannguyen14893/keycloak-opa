package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.constans.LogAttributeConstants;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.ErrorResultResponse;
import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import com.charter.tech.keycloakopa.service.ErrorCatalogService;
import com.charter.tech.keycloakopa.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private Utils utils;
    @Autowired
    private DBMessageSourceConfig dbMessageSourceConfig;
    @Autowired
    private ErrorCatalogService errorCatalogService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        String code = ResponseCodeConstants.SEC_AUTH_INVALID_TOKEN;
        if (ex.getCause() instanceof BadJwtException) {
            code = ResponseCodeConstants.SEC_AUTH_INVALID_TOKEN;
        } else if (ex.getCause() instanceof AuthenticationCredentialsNotFoundException) {
            code = ResponseCodeConstants.SEC_AUTH_BAD_CREDENTIALS;
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8);
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(code);
        response.setStatus(errorCatalog.getHttpStatus());
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, request.getLocale());
        ErrorResultResponse<Map<String, String>> resultResponse = new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), code, messages,
                errorCatalog.getSeverity(), errorCatalog.getRetryable(), Map.of("error", ex.getMessage()));
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(resultResponse));
    }
}
