package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.constans.LogAttributeConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
import java.util.UUID;

@Component
@Slf4j
@Order(1)
public class LoggingFilterConfig extends OncePerRequestFilter {
    @Value("${spring.application.name:application}")
    private String appName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDCPut(request, UUID.randomUUID().toString());
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 0);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(wrappedRequest, wrappedResponse);
        try {
            String requestBody = new String(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());
            String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
            log.info("***** REQUEST ***** : {}", requestBody.replaceAll("\\s+", ""));
            log.info("***** RESPONSE *****: {}", responseBody);
        } finally {
            wrappedResponse.copyBodyToResponse();
            MDC.clear();
        }
    }

    private void MDCPut(HttpServletRequest request, String traceId) {
        try {
            MDC.put(LogAttributeConstants.TRACE_ID, traceId);
            MDC.put(LogAttributeConstants.APPLICATION_NAME, appName);
            MDC.put(LogAttributeConstants.HTTP_METHOD, request.getMethod());
            MDC.put(LogAttributeConstants.REQUEST_URL, request.getRequestURI());
            MDC.put(LogAttributeConstants.IP_ADDRESS, InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            MDC.put(LogAttributeConstants.IP_ADDRESS, "unknown");
            log.warn("Could not determine local host address", e);
        }
    }
}
