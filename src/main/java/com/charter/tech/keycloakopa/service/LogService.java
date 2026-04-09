package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.config.BufferingClientHttpResponseWrapper;
import com.charter.tech.keycloakopa.constans.LogAttributeConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class LogService {
    private static final int MAX_BODY_SIZE = 10_000;

    public void logCombined(
            HttpRequest request,
            byte[] requestBody,
            BufferingClientHttpResponseWrapper response,
            long durationMs,
            Exception error) throws IOException {

        Map<String, Object> http = new LinkedHashMap<>();

        http.put("direction", "OUTBOUND");
        http.put("method", request.getMethod().name());
        http.put("uri", request.getURI().toString());
        http.put("requestHeaders", getMaskedHeaders(request.getHeaders().toSingleValueMap()));
        http.put("requestBody", getContentBody(requestBody));

        if (response != null) {
            http.put("status", response.getStatusCode().value());
            http.put("responseBody", getContentBody(response.getBody().readAllBytes()));
        }

        if (error != null) {
            http.put("status", "ERROR");
            http.put("error", error.getMessage());
        }

        http.put("durationMs", durationMs);

        // Carry requestId from MDC (set by inbound filter)
        String requestId = MDC.get("requestId");
        if (requestId != null) http.put("requestId", requestId);

        int status = response != null ? response.getStatusCode().value() : 0;

        if (error != null || status >= 500) {
            log.error("HTTP CLIENT {} {} -> {} ({}ms)",
                    request.getMethod(), request.getURI(), status, durationMs,
                    StructuredArguments.keyValue("http_client", http));
        } else if (status >= 400) {
            log.warn("HTTP CLIENT {} {} -> {} ({}ms)",
                    request.getMethod(), request.getURI(), status, durationMs,
                    StructuredArguments.keyValue("http_client", http));
        } else {
            log.info("HTTP CLIENT {} {} -> {} ({}ms)",
                    request.getMethod(), request.getURI(), status, durationMs,
                    StructuredArguments.keyValue("http_client", http));
        }
    }

    private Map<String, String> getMaskedHeaders(Map<String, String> headers) {
        Map<String, String> masked = new LinkedHashMap<>();
        headers.forEach((k, v) -> masked.put(k, isSensitiveHeader(k) ? "*****" : v));
        return masked;
    }

    public void logCombined(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            long duration) {

        int status = response.getStatus();
        if (StringUtils.isNotBlank(request.getHeader(LogAttributeConstants.TRACE_ID))) {
            MDC.put(LogAttributeConstants.TRACE_ID, request.getHeader(LogAttributeConstants.TRACE_ID));
        }
        Map<String, Object> http = new LinkedHashMap<>();
        http.put("direction", "INBOUND");
        http.put("method", request.getMethod());
        http.put("uri", request.getRequestURI());
        http.put("query", request.getQueryString());
        http.put("clientIp", getClientIp(request));
        http.put("requestHeaders", getMaskedHeaders(request));
        http.put("requestBody", getContentBody(request.getContentAsByteArray()));

        // --- Response fields ---
        http.put("status", status);
        http.put("durationMs", duration);
        http.put("responseBody", getContentBody(response.getContentAsByteArray()));
        // Log level based on status
        if (status >= 500) {
            log.error("HTTP {} {} -> {} ({}ms)",
                    request.getMethod(), request.getRequestURI(), status, duration,
                    StructuredArguments.keyValue("http", http));
        } else if (status >= 400) {
            log.warn("HTTP {} {} -> {} ({}ms)",
                    request.getMethod(), request.getRequestURI(), status, duration,
                    StructuredArguments.keyValue("http", http));
        } else {
            log.info("HTTP {} {} -> {} ({}ms)",
                    request.getMethod(), request.getRequestURI(), status, duration,
                    StructuredArguments.keyValue("http", http));
        }
    }

    public Map<String, String> getMaskedHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Collections.list(request.getHeaderNames()).forEach(name ->
                headers.put(name, isSensitiveHeader(name) ? "*****" : request.getHeader(name))
        );
        return headers;
    }

    public String getContentBody(byte[] content) {
        if (content.length == 0) return null;
        if (content.length > MAX_BODY_SIZE) return "[too large: " + content.length + " bytes]";
        try {
            return maskSensitiveFields(new String(content, StandardCharsets.UTF_8));
        } catch (Exception e) {
            return "[unreadable]";
        }
    }

    public String maskSensitiveFields(String body) {
        if (body == null) return null;
        return body
                .replaceAll("(\"password\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"token\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"secret\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"creditCard\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"ssn\"\\s*:\\s*\")[^\"]+\"", "$1*****\"");
    }

    public boolean isSensitiveHeader(String name) {
        String lower = name.toLowerCase();
        return lower.contains("authorization") ||
                lower.contains("cookie") ||
                lower.contains("x-api-key");
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        return (forwarded != null) ? forwarded.split(",")[0].trim() : request.getRemoteAddr();
    }

    public boolean shouldSkip(String uri) {
        return uri.startsWith("/actuator") ||
                uri.startsWith("/health") ||
                uri.startsWith("/static") ||
                uri.endsWith(".css") ||
                uri.endsWith(".js") ||
                uri.endsWith(".html") ||
                uri.endsWith(".png") ||
                uri.startsWith("/v3/api-docs") ||
                uri.endsWith(".ico");
    }
}
