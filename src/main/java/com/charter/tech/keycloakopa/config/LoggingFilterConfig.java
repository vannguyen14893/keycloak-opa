package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.constans.LogAttributeConstants;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@Order(1)
public class LoggingFilterConfig extends OncePerRequestFilter {
    @Value("${spring.application.name:application}")
    private String appName;
    private static final int MAX_BODY_SIZE = 10_000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldSkip(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request,0);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logCombined(wrappedRequest, wrappedResponse, duration);
            wrappedResponse.copyBodyToResponse();
            MDC.clear();
        }

    }

    private void logCombined(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            long duration) {

        int status = response.getStatus();
        Map<String, Object> http = new LinkedHashMap<>();
        http.put("method", request.getMethod());
        http.put("uri", request.getRequestURI());
        http.put("query", request.getQueryString());
        http.put("clientIp", getClientIp(request));
        http.put("requestHeaders", getMaskedHeaders(request));
        http.put("requestBody", getRequestBody(request));

        // --- Response fields ---
        http.put("status", status);
        http.put("durationMs", duration);
        http.put("responseBody", getResponseBody(response));

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

    private Map<String, String> getMaskedHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Collections.list(request.getHeaderNames()).forEach(name ->
                headers.put(name, isSensitiveHeader(name) ? "*****" : request.getHeader(name))
        );
        return headers;
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) return null;
        if (content.length > MAX_BODY_SIZE) return "[too large: " + content.length + " bytes]";
        try {
            return maskSensitiveFields(new String(content, request.getCharacterEncoding()));
        } catch (Exception e) {
            return "[unreadable]";
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length == 0) return null;
        if (content.length > MAX_BODY_SIZE) return "[too large: " + content.length + " bytes]";
        try {
            return maskSensitiveFields(new String(content, StandardCharsets.UTF_8));
        } catch (Exception e) {
            return "[unreadable]";
        }
    }

    private String maskSensitiveFields(String body) {
        if (body == null) return null;
        return body
                .replaceAll("(\"password\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"token\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"secret\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"creditCard\"\\s*:\\s*\")[^\"]+\"", "$1*****\"")
                .replaceAll("(\"ssn\"\\s*:\\s*\")[^\"]+\"", "$1*****\"");
    }

    private boolean isSensitiveHeader(String name) {
        String lower = name.toLowerCase();
        return lower.contains("authorization") ||
                lower.contains("cookie") ||
                lower.contains("x-api-key");
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        return (forwarded != null) ? forwarded.split(",")[0].trim() : request.getRemoteAddr();
    }

    private boolean shouldSkip(String uri) {
        return uri.startsWith("/actuator") ||
                uri.startsWith("/health") ||
                uri.startsWith("/static") ||
                uri.endsWith(".css") ||
                uri.endsWith(".js") ||
                uri.endsWith(".html") ||
                uri.endsWith(".png") ||
                uri.startsWith("/v3/api-docs")||
                uri.endsWith(".ico");
    }
}
