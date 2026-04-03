package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    private final LogService logService;
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long start = System.nanoTime();
        ClientHttpResponse response = null;
        try {
            response = execution.execute(request, body);
            BufferingClientHttpResponseWrapper buffered = new BufferingClientHttpResponseWrapper(response);
            long durationMs = (System.nanoTime() - start) / 1_000_000;
            logService.logCombined(request, body, buffered, durationMs, null);
            return buffered;
        } catch (Exception ex) {
            long durationMs = (System.nanoTime() - start) / 1_000_000;
            logService.logCombined(request, body, null, durationMs, ex);
            throw ex;
        }
    }



}
